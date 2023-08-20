package com.manhnd.partition;

import org.apache.kafka.clients.producer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class PartitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartitionApplication.class, args);
		String topicName = "profileOnboarding";
		int partitionNumber = 0; // Partition mà bạn muốn gửi tin nhắn tới

		// Cấu hình producer
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);

		String key = "my-key"; // Key để xác định partition
		String message = "Hello Kafka!"; // Nội dung tin nhắn

		// Gửi tin nhắn tới partition mong muốn
		ProducerRecord<String, String> record = new ProducerRecord<>(topicName, partitionNumber, key, message);
		producer.send(record, new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					exception.printStackTrace();
				} else {
					System.out.println("Sent message to partition " + metadata.partition()
							+ ", offset " + metadata.offset());
				}
			}
		});

		producer.close();
	}
	}


