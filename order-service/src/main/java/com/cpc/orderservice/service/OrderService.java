package com.cpc.orderservice.service;


import java.time.LocalDateTime;
import java.util.List;

import com.cpc.orderservice.models.Order;

public interface OrderService {
	Order submitOrder(String stationId, Order order);
	String checkOrderStatus(String id);
	String confirmOrderReceival(String id);
	List<Order> fetchAllOrders(); 
	Order updateOrderAllocation(String id); //emitted from inventory service
	Order updateOrderSchedule(String id,LocalDateTime scheduledDateTime); //emitted from schedule service
	Order updateOrderDispatch(String id,LocalDateTime dispatchedDateTime); //emitted from dispatch service
}
