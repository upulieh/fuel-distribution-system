package com.cpc.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.cpc.orderservice.OrderServiceApplication;

//creating topics
@Configuration
public class KafkaTopicConfig {
	
	// registers a NewTopic bean for each topic to the application context.
	// If the topic already exists, the bean will be ignored.
	@Bean
	public NewTopic getNewOrderSubmitTopic() {
		OrderServiceApplication.logger.info("orderser-service :Created orderSubmitTopic topic");
		return TopicBuilder.name("orderSubmitTopic").build();
	}

	@Bean
	public NewTopic getNewInventorySubmitTopic() {
		OrderServiceApplication.logger.info("orderser-service :Created inventorySubmitTopic topic");
		return TopicBuilder.name("inventorySubmitTopic").build();
	}

	@Bean
	public NewTopic getNewScheduleSubmitTopic() {
		OrderServiceApplication.logger.info("orderser-service :Created scheduleSubmitTopic topic");
		return TopicBuilder.name("scheduleSubmitTopic").build();
	}

	@Bean
	public NewTopic getNewDispatchSubmitTopic() {
		OrderServiceApplication.logger.info("orderser-service :Created dispatchSubmitTopic topic");
		return TopicBuilder.name("dispatchSubmitTopic").build();
	}
}
