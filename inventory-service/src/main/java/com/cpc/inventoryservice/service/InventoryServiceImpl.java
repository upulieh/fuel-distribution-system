package com.cpc.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpc.inventoryservice.repository.InventoryRepository;
import com.cpc.orderservice.models.Order;

@Service
public class InventoryServiceImpl implements InventoryService{
	
	@Autowired
	InventoryRepository inventoryRepository;

	//Updating using MongoTemplate
//	@Override 
//	public Order updateOrderAllocationStatus(String stationId, Order order) {
//		System.out.println("Updating station " + stationId +" order...");
////		inventoryRepository.updateItemQuantity(name, newQuantity);
//		return null;
//	}	
}
