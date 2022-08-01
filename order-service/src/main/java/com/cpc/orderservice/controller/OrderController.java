package com.cpc.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpc.orderservice.models.Order;
import com.cpc.orderservice.service.OrderService;

//this restful API allows us to publish messages to a queue
@RestController
@RequestMapping(value = "/services")
public class OrderController {

	@Autowired
	OrderService orderService;

	// used to inject into method params
	private KafkaTemplate<String, Order> orderKafkaTemplate;

	// constructor
	public OrderController(KafkaTemplate<String, Order> orderKafkaTemplate) {
		this.orderKafkaTemplate = orderKafkaTemplate;
	}

	// submit an order
	// POST, http://localhost:8191/services/orders (with JSON body of Order payload)
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	@PostMapping // to expose to post requests in kafka
	public Order submitOrder(@RequestBody Order order) {
		Order o = orderService.submitOrder(order.getStationId(), order);
		orderKafkaTemplate.send("orderSubmitTopic", "Order for " + o.getId() + " submitted", o); // to kafka
		return o;
	}

	// check the status of an order
	// GET, http://localhost:8191/services/orders/{id}
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	public String checkOrderStatus(@PathVariable(value = "id") String id) {
		String status = orderService.checkOrderStatus(id);
		return status;
	}

	// update the "received" state of an order
	// POST, http://localhost:8191/services/orders/{id}
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.POST)
	public String confirmOrderReceival(@PathVariable(value = "id") String id) {
		String status = orderService.confirmOrderReceival(id);
		return status;
	}
	
	// retreive all orders
	// GET, http://localhost:8191/services/orders
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public Object fetch() {
		List<Order> orders = orderService.fetchAllOrders();

		if (orders == null) {
			//ResponseEntity.notFound().build();
			return "Order list is empty.";
		} else {
			//ResponseEntity.ok().body(customers);
			return orders;
		}
	}
}
