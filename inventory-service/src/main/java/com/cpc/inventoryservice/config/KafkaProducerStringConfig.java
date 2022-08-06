package com.cpc.inventoryservice.config;

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
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.cpc.orderservice.models.Order;

//only used for creating the KafkaTemplate for string
@Configuration
class KafkaProducerStringConfig {

	@Value("${spring.kafka.bootstrap-servers}") // refers application.yml file
	private String bootstrapServers;

	// configuration to pass to a producer factory
	@Bean
	public Map<String, Object> sProducerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}

	// defining the producer factory (responsible for creating producer instances)
	@Bean
	public ProducerFactory<String, String> sInventoryProducerFactory() {
		return new DefaultKafkaProducerFactory<>(sProducerConfigs());
	}

//	KafkaTemplate helps us to send messages to their respective topic
	@Bean
	public KafkaTemplate<String, String> sOrderKafkaTemplate() {
		return new KafkaTemplate<>(sInventoryProducerFactory());
	}
}