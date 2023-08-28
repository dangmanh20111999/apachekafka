package com.manhnd.accountservice.kafkaconfig;

import com.manhnd.commonservice.utils.Constant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    NewTopic onBoardedProfile() {
        return new NewTopic(Constant.PROFILE_ONBOARDED_TOPIC, 3, (short) 1);
    }
}
