package com.cpc.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cpc.orderservice.models.Order;
import com.cpc.orderservice.service.OrderService;

//this restful API allows us to publish messages to a queue
@RestController
@RequestMapping(value="/services")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	//used to inject into method params
	private KafkaTemplate<String, String> kafkaTemplate;
	
	//constructor
	public OrderController(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	

	// POST, http://localhost:8191/services/orders (with JSON body of Order payload)
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	@PostMapping //to expose to post requests in kafka
	public Order submitOrder(@RequestBody Order order) {
		Order o = orderService.submitOrder(order.getStationId(),order);
		kafkaTemplate.send("orderSubmitTopic","Order for "+o.getId()+" submitted");	
		return o; 
	}
}