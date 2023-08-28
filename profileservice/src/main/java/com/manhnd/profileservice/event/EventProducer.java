package com.manhnd.profileservice.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@Slf4j
public class EventProducer {
    @Autowired
    private KafkaSender<String,String> sender;

//    @Autowired
//    private KafkaTemplate<String,String> kafkaTemplate;

    public Mono<String> send(String topicName,int partitionNumber, String message, String key){
//        ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, key, message);
//        return sender.send(record).then().thenReturn("OK");
        return sender.send(Mono.just(SenderRecord.create(new ProducerRecord<>(topicName, partitionNumber, key, message),message)))
                .then()
                .thenReturn("OK");
    }

//    public void sendMessageToPartition(String topic, String message, String key, int partition) {
//        ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, key, message);
//        kafkaTemplate.send(record);
//    }
}
