package com.cpc.orderservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

 
@SpringBootApplication
@EnableMongoRepositories
@EntityScan("com.cpc.orderservice.models")
public class OrderServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
	
	//	sending a message to a topic
	// parameters use dependency injection from file KafkaProducerConfig
//	@Bean
//	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
//		// there are many ways to send this message (with different params)
//		return args -> {
//			// param1 - topic name from KafkaTopicConfig
//			kafkaTemplate.send("orderSubmitTopic","New order submitted!");
//		};
//	}
}
