package com.cpc.inventoryservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpc.inventoryservice.repository.InventoryRepository;
import com.cpc.inventoryservice.repository.QuotaRepository;
import com.cpc.orderservice.models.Order;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	InventoryRepository inventoryRepository;

	@Override
	public String updateOrderAllocation(String id) {
		System.out.println("inside updateOrderAllocation service");
		Optional<Order> order = inventoryRepository.findById(id);

		System.out.println("Found the previous order "+order);
		
		if (order.isPresent()) {
			System.out.println("order is present");
			System.out.println("Changing values of order :" + id);
			return updateAllocatedField(order.get());
		} else {
			//where it is stuck
			//issue : goes in here 
			//pressumable reason: the document from the order service is not obtainable from here?
			System.out.println("Order is not present"); 		
			
			return "Error occured while fetching the order from the db";
		}
	}

	private String updateAllocatedField(Order order) {
		// update values in inventory db
		order.setAllocated(true);
		order.setAllocatedTime(LocalDateTime.now());
		System.out.println("About to save the \"allocated\" for order");
		Order o = inventoryRepository.save(order);
		System.out.println("Saved " + o);

		return "Quota Allocated successfully!";
	}
}
