package com.cpc.inventoryservice.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import com.cpc.inventoryservice.model.Quota;
import com.cpc.inventoryservice.service.InventoryService;
import com.cpc.inventoryservice.service.QuotaService;
import com.cpc.orderservice.models.Order;

@Controller
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@Autowired
	QuotaService quotaService;

	//protected final Logger LOG = LoggerFactory.getLogger(InventoryController.class); // the class listening to kafka

	// Since we have multiple listener containers, we are specifying which container
	// factory to use.
	// If we don’t specify the containerFactory attribute it defaults to
	// kafkaListenerContainerFactory which uses StringSerializer and
	// StringDeserializer in our case.
	@KafkaListener(topics = "orderSubmitTopic", groupId = "cpc", containerFactory = "orderKafkaListenerContainerFactory")
	void listener(Order order) {
//		LOG.info("Inventory Listener received order: ", order);
		System.out.println("Listener received order: "+order);

		// chanage order db inventory value to true
//		System.out.println("before updateOrderAllocation, inside controller");
		inventoryService.updateOrderAllocation(order.getId());
		System.out.println("after updateOrderAllocation, inside controller");

		// retrieve the availableQuantity from inventory
		Integer latestAvailableQuantity = 0; // do this

		// create Quota and save it
		Quota quota = new Quota(LocalDateTime.now().toString(), order.getId());
		quotaService.submitQuotaRecord(quota, order.getQuantity(), latestAvailableQuantity);
	}

	public void initializeInventory() {
		quotaService.initializeInventory();
	}
}
