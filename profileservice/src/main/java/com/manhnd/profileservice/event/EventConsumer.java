package com.manhnd.profileservice.event;

import com.google.gson.Gson;
import com.manhnd.commonservice.utils.Constant;
import com.manhnd.profileservice.dto.ProfileDTO;
import com.manhnd.profileservice.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;

@Service
@Slf4j
public class EventConsumer {
    Gson gson = new Gson();
    @Autowired
    ProfileService profileService;
    public EventConsumer(ReceiverOptions<String, String> receiverOptions){
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.PROFILE_ONBOARDED_TOPIC)))
                .receive().subscribe(this::profileOnboarded);
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.PROFILE_UPDATE_ROLE_TOPIC)))
                .receive().subscribe(this::profileUpdateRole);
    }
    public void profileOnboarded(ReceiverRecord<String,String> receiverRecord){
        log.info(receiverRecord.value());
        ProfileDTO dto = gson.fromJson(receiverRecord.value(),ProfileDTO.class);
        profileService.updateStatusProfile(dto).subscribe();
    }

    public void profileUpdateRole(ReceiverRecord<String,String> receiverRecord) {
        ProfileDTO dto = gson.fromJson(receiverRecord.value(), ProfileDTO.class);
        profileService.updateRole(dto).subscribe();
    }
}
