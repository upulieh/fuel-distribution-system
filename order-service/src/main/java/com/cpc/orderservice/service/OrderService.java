package com.cpc.orderservice.service;


import com.cpc.orderservice.models.Order;

public interface OrderService {
	Order submitOrder(String stationId, Order order);
//	Order checkOrderStatus(String id);
//	void confirmOrderReceival(String id);
//	List<Order> fetchAllOrders(); 
}
