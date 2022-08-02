package com.cpc.inventoryservice.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

import com.cpc.inventoryservice.InventoryServiceApplication;
import com.cpc.inventoryservice.model.Quota;
import com.cpc.inventoryservice.service.QuotaService;
import com.cpc.orderservice.models.Order;

@Controller
public class InventoryController {

	// to send the order db change to kafka
	private KafkaTemplate<String, Order> inventoryKafkaTemplate;

	// constructor
	public InventoryController(KafkaTemplate<String, Order> inventoryKafkaTemplate) {
		this.inventoryKafkaTemplate = inventoryKafkaTemplate;
	}

	@Autowired
	QuotaService quotaService;

	// Since we have multiple listener containers, we are specifying which container
	// factory to use.
	@KafkaListener(topics = "orderCreateTopic", groupId = "cpc", containerFactory = "inventoryKafkaListenerContainerFactory")
	void listener(Order order) {

		// create Quota and save it
		Quota quota = new Quota(LocalDateTime.now().toString(), order.getId());
		Quota q = quotaService.submitQuotaRecord(quota, order.getQuantity(), order.getFuelType());

		if (q == null) {
			// not enough stock
			InventoryServiceApplication.logger
					.info("inventory-service : Can't suffice order :" + order.getId() + " due to running out of stock");
			// these should be put in a separate table for future reference on trying again
			// for fuel
		} else {
			// success
			// change order db inventory value to true (order service through kafka)
			inventoryKafkaTemplate.send("inventorySubmitTopic", "Order for " + order.getId() + " allocated", order);
		}
	}

	public void initializeInventory(int initialQuantityO92, int emergencyAllocationO92, int initialQuantityO95,
			int emergencyAllocationO95, int initialQuantityRD, int emergencyAllocationRD, int initialQuantitySD,
			int emergencyAllocationSD) {
		quotaService.initializeInventory(initialQuantityO92, emergencyAllocationO92, initialQuantityO95,
				emergencyAllocationO95, initialQuantityRD, emergencyAllocationRD, initialQuantitySD,
				emergencyAllocationSD);
	}
}
