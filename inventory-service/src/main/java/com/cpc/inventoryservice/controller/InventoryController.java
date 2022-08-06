package com.cpc.inventoryservice.controller;

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
	private KafkaTemplate<String, String> sInventoryKafkaTemplate;

	// constructor
	public InventoryController(KafkaTemplate<String, Order> inventoryKafkaTemplate,
			KafkaTemplate<String, String> sInventoryKafkaTemplate) {
		this.inventoryKafkaTemplate = inventoryKafkaTemplate;
		this.sInventoryKafkaTemplate = sInventoryKafkaTemplate;
	}

	@Autowired
	QuotaService quotaService;

	// Since we have multiple listener containers, we are specifying which container
	// factory to use.
	@KafkaListener(topics = "orderCreateTopic", groupId = "cpc", containerFactory = "inventoryKafkaListenerContainerFactory")
	void listener(Order order) {

		// create Quota from the previous Quota's values
		Quota q = quotaService.submitQuotaRecord(order.getId(), order.getQuantity(), order.getFuelType());

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

	// use a common container factory for all projects
	@KafkaListener(topics = "quantityUpdateTopic", groupId = "cpc", containerFactory = "sOrderKafkaListenerContainerFactory")
	void listener(String id) {
		//create a "quantity" field under Quota
		Quota q = quotaService.updateQuantities(id);
		
		if (q == null) {
			InventoryServiceApplication.logger
					.info("inventory-service : (order serive -> dispatch service) couldn't update quantities");
		} else {
			InventoryServiceApplication.logger
					.info("inventory-service : (order serive -> dispatch service) updated quantities for" + id);
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
