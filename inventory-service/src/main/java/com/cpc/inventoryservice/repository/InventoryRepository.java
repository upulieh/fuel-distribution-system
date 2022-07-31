package com.cpc.inventoryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cpc.orderservice.models.Order;

public interface InventoryRepository extends MongoRepository<Order, String>{
	
}
