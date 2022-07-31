package com.cpc.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cpc.orderservice.models.Order;
import com.cpc.orderservice.service.OrderService;

@RestController
@RequestMapping(value="/services")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	

	// POST, http://localhost:8191/services/orders (with JSON body of Order payload)
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public Order submitOrder(@RequestBody Order order) {
		return orderService.submitOrder(order.getStationId(),order);
	}
}
