package com.manhnd.profileservice.kafkaconfig;

import com.manhnd.commonservice.utils.Constant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {
    @Bean
    NewTopic onBoardingProfile() {
        return new NewTopic(Constant.PROFILE_ONBOARDING_TOPIC, 3, (short) 1);
    }
}
