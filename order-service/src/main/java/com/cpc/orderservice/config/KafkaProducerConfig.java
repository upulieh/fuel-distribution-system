package com.cpc.orderservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}") //refers application.yml file
	private String bootstrapServers;

	// configuration to pass to a producer factory
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>(); // key - String - name, value - Object - whatever we want to pass
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); //Host and port on which Kafka is running on
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); //Serializer class to be used for the key
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); //Serializer class to be used for the value
		return props;
	}

	// defining the producer factory (responsible for creating producer instances)
	// key - String - name
	// value - String - you can change this to whatever the object you want to pass
	// (Eg: Customer, Student, Notification)
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

//	KafkaTemplate helps us to send messages to their respective topic
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}