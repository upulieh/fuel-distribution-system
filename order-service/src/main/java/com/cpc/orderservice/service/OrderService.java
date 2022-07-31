package com.cpc.orderservice.service;

import java.util.List;

import com.cpc.orderservice.models.Order;

public interface OrderService {
	Order submitOrder(String stationId, Order order);
//	Order checkOrderStatus(String id);
//	void confirmOrderReceival(String id);
//	List<Order> fetchAllOrders(); 
}
