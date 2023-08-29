package com.manhnd.commonservice.commons;

import com.manhnd.commonservice.configuration.ReactiveKafkaAppProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ConfigProducer {

    @Autowired
    private ReactiveKafkaAppProperties reactiveKafkaAppProperties;

    public Properties kafkaConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, reactiveKafkaAppProperties.bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, reactiveKafkaAppProperties.consumerGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
}
