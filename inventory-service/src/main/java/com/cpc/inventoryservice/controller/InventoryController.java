package com.cpc.inventoryservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import com.cpc.orderservice.models.Order;

@Controller
public class InventoryController {
	
	protected final Logger LOG = LoggerFactory.getLogger(InventoryController.class); // the class listening to kafka

	// Since we have multiple listener containers, we are specifying which container
	// factory to use.
	// If we don’t specify the containerFactory attribute it defaults to
	// kafkaListenerContainerFactory which uses StringSerializer and
	// StringDeserializer in our case.
	@KafkaListener(topics = "orderSubmitTopic", groupId = "cpc", containerFactory = "orderKafkaListenerContainerFactory")
	void listener(Order order) {
		LOG.info("Inventory Listener [{}]", order);
	}
}
