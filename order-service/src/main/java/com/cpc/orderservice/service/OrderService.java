package com.cpc.orderservice.service;


import java.util.List;

import com.cpc.orderservice.models.Order;

public interface OrderService {
	Order submitOrder(String stationId, Order order);
	String checkOrderStatus(String id);
	String confirmOrderReceival(String id);
	List<Order> fetchAllOrders(); 
	Order updateOrderAllocation(String id); //emitted from inventory server
}
