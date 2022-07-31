package com.cpc.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

 
@SpringBootApplication
@EnableMongoRepositories
@EntityScan("com.cpc.orderservice.models")
public class OrderServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
