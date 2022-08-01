package com.cpc.orderservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cpc.orderservice.models.Order;

public interface OrderRepository extends MongoRepository<Order, String>{
	
}
