package com.cpc.inventoryservice;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cpc.inventoryservice.controller.InventoryController;
import com.cpc.orderservice.OrderServiceApplication;
import com.cpc.orderservice.config.KafkaTopicConfig;

@SpringBootApplication
public class InventoryServiceApplication {

	@Autowired
	InventoryController inventoryController;
	
	public static final Logger logger = LoggerFactory.getLogger(InventoryServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
		
    @Bean
    CommandLineRunner initializeInventory() {
        return args -> {
            // initial stock  - 50 000 litres, initial emergency allocation - 0 (for each fuel type)
    		inventoryController.initializeInventory(50_000,0,50_000,0,50_000,0,50_000,0);  
    		InventoryServiceApplication.logger.info("inventory-service :Initialized inventory");
        };
    }
}
