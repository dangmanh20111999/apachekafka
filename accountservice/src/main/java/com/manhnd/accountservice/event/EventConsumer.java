package com.manhnd.accountservice.event;

import com.google.gson.Gson;
import com.manhnd.accountservice.dto.AccountDTO;
import com.manhnd.accountservice.service.AccountService;
import com.manhnd.commonservice.commons.ConfigProducer;
import com.manhnd.commonservice.model.ProfileDTO;
import com.manhnd.commonservice.utils.Constant;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class EventConsumer {
    private static int partitionNumber = 1;
    private static int numberPt = 0;
    private static String key = "ManhND43@fpt.com";
    Gson gson = new Gson();
    @Autowired
    AccountService accountService;

    @Autowired
    EventProducer eventProducer;


    @Autowired
    ReceiverOptions<String,String> receiverOptions;

    @Autowired
    ConfigProducer configProducer;

    public EventConsumer(ReceiverOptions<String,String> receiverOptions){

        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.PROFILE_ONBOARDING_TOPIC)))
                .receive().subscribe(this::profileOnboarding);
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.UPDATE_INITIALBLANCE_TOPIC)))
                .receive().subscribe(this::profileUpdateInitialBalance);
    }

    public String getValueInOffet(String topicName, Integer partitionNumber, Integer offsetNumber) {
        String value = null;
        if (configProducer != null) {
            Properties props = configProducer.kafkaConsumer();
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            List<PartitionInfo> partitions = consumer.partitionsFor(topicName);
            PartitionInfo partitionInfo = partitions.get(partitionNumber);
            TopicPartition topicPartition = new TopicPartition(topicName, partitionNumber);
            consumer.assign(Collections.singletonList(topicPartition));
            consumer.seekToBeginning(Collections.singletonList(topicPartition));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                if (record.offset() == offsetNumber) {
                    value = record.value();
                    break;
                }
            }
            consumer.close();
        }
        return value;
    }



    public void profileOnboarding(ReceiverRecord<String,String> receiverRecord){
        String data = getValueInOffet(Constant.PROFILE_ONBOARDING_TOPIC, 1 , 0);
        log.info("Value " ,data);
        log.info("Profile Onboarding event",receiverRecord.value());
        ProfileDTO dto = gson.fromJson(data,ProfileDTO.class);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(dto.getEmail());
        accountDTO.setReserved(0);
        accountDTO.setBalance(dto.getInitialBalance());
        accountDTO.setCurrency("USD");
        accountService.createNewAccount(accountDTO).subscribe(res ->{
            dto.setStatus(Constant.STATUS_PROFILE_ACTIVE);
            eventProducer.send(Constant.PROFILE_ONBOARDED_TOPIC,gson.toJson(dto), partitionNumber, key).subscribe();
        });
    }

    public void profileUpdateInitialBalance(ReceiverRecord<String,String> receiverRecord) {
        ProfileDTO dto = gson.fromJson(receiverRecord.value(),ProfileDTO.class);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(dto.getInitialBalance());
        accountDTO.setEmail(dto.getEmail());
        accountService.updateInitialBalance(accountDTO).subscribe(res -> {
            dto.setRole(Constant.ROLE_ADMIN);
            eventProducer.send(Constant.PROFILE_UPDATE_ROLE_TOPIC, gson.toJson(dto), partitionNumber, key).subscribe();
        });
    }
}
