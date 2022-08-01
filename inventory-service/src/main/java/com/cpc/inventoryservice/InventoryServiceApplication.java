package com.cpc.inventoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cpc.inventoryservice.controller.InventoryController;

@SpringBootApplication
public class InventoryServiceApplication {

	@Autowired
	InventoryController inventoryController;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	// adding the initial stock on the inventory as 50 000 litres	
    @Bean
    CommandLineRunner initializeInventory() {
        return args -> {
    		inventoryController.initializeInventory();
    		System.out.println("Initialized inventory");
        };
    }
}
