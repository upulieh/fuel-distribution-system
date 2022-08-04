package com.cpc.scheduleservice.controller;

import java.time.LocalDateTime;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

import com.cpc.orderservice.models.Order;
import com.cpc.scheduleservice.dto.SDDTO;
import com.cpc.scheduleservice.model.Schedule;
import com.cpc.scheduleservice.service.ScheduleService;

@Controller
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;

	// send to the order service for updating
	private KafkaTemplate<String, Order> scheduleKafkaTemplate;
	private KafkaTemplate<String, SDDTO> sDKafkaTemplate; // changed according to the dto

//	// send to the dispatch service for updating 
//	private KafkaTemplate<String, String[]> scheduleStringKafkaTemplate;	

	// constructor
	public ScheduleController(KafkaTemplate<String, Order> scheduleKafkaTemplate,
			KafkaTemplate<String, SDDTO> sDKafkaTemplate) {
		this.scheduleKafkaTemplate = scheduleKafkaTemplate;
		this.sDKafkaTemplate = sDKafkaTemplate;
	}

	@KafkaListener(topics = "scheduleCreateTopic", groupId = "cpc", containerFactory = "scheduleKafkaListenerContainerFactory")
	void listener(Order order) {
		System.out.println("Received to schedule service " + order.toString());

		// gives random time within next hour from now (change 5 to 59)
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime randomDateTime = now.plusMinutes(ThreadLocalRandom.current().nextInt(59));

		// random date within the next seven days
//		LocalDateTime now = LocalDateTime.now();
//		LocalDateTime randomDateTime = now.plusDays(ThreadLocalRandom.current().nextInt(6));

		System.out.println("Random date is " + randomDateTime);

		// send back to order service for saving
		order.setScheduled(true);
		order.setScheduledTime(randomDateTime);
		scheduleKafkaTemplate.send("scheduleSubmitTopic", "Order for " + order.getId() + " scheduled", order);

		// forward to the dispatch service
		// dispatch only sees the allocated quantity

		// allocation quantity becomes 0 after dispatch
		// stock is deducted from the actually available quantity

		// reservation - impact on the allocated stock
		// dispatch - impact on the available stock

		// create Schedule record and save it
		Schedule schedule = new Schedule(LocalDateTime.now().toString(), order.getId(), randomDateTime);
		Schedule s = scheduleService.submitScheduleRecord(schedule);

		// enum to int
		int q = Integer.parseInt((order.getQuantity().toString()).replace("L", "").replace("_", ""));
		System.out.println("Quantity is :" + q);

		// forward necessary information to dispatch service
		SDDTO sDDTO = new SDDTO(order.getScheduledTime(), order.getId(), order.getStationId(), q);
		System.out.println("Forwarding SDDTO " + sDDTO + " to dispatching service. ");
		sDKafkaTemplate.send("dispatchCreateTopic",
				"Kafka : Submitted order " + order.getId() + " to dispatchCreateTopic", sDDTO);

	}
}
