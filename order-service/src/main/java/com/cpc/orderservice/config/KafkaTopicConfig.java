package com.cpc.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//creating topics
@Configuration
public class KafkaTopicConfig {
	// registers a NewTopic bean for each topic to the application context.
	// If the topic already exists, the bean will be ignored.
	// KafkaAdmin increases the number of partitions
	// if it finds that an existing topic has fewer partitions than
	// NewTopic.numPartitions.
	@Bean
	public NewTopic getNewOrderSubmitTopic() {
		return TopicBuilder.name("orderSubmitTopic").build();
	}

	@Bean
	public NewTopic getNewInventorySubmitTopic() {
		return TopicBuilder.name("inventorySubmitTopic").build();
	}

	@Bean
	public NewTopic getNewScheduleSubmitTopic() {
		return TopicBuilder.name("scheduleSubmitTopic").build();
	}

	@Bean
	public NewTopic getNewDispatchSubmitTopic() {
		return TopicBuilder.name("dispatchSubmitTopic").build();
	}
}
