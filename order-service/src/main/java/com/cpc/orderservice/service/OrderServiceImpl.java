package com.cpc.orderservice.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpc.orderservice.models.Order;
import com.cpc.orderservice.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Override
	public Order submitOrder(String stationId, Order order) {
//		setting values for the order
		String createdRefId = createId(stationId);
		order.setId(createdRefId);
		order.setReservedTime(LocalDateTime.now());
		order.setReserved(true);
		
		//kafka integration should happen here
		return orderRepository.save(order);
	}

	public String createId(String stationId) {
		int uniqueId = (int) ((new Date().getTime() / 10000000L) % Integer.MAX_VALUE); // creating a unique value of 8 numbers
		return stationId.substring(0, 4) + stationId.charAt(stationId.length()-1) + uniqueId;
	}

//	@Override
//	public Order checkOrderStatus(String id) {
//		Optional<Order> order = orderRepository.findById(id);
//
//		if (order.isPresent()) {
//			return order.get();
//		}
//		return null; // handle null exception in the controller
//	}
//
//	@Override
//	public void confirmOrderReceival(String id) {
//
//	}
//
//	@Override
//	public List<Order> fetchAllOrders() {
//		Iterable<Order> orders = orderRepository.findAll();
//
//		List<Order> list = new ArrayList<Order>();
//
//		orders.forEach(c -> {
//			list.add(c);
//		});
//
//		return list;
//	}
}
