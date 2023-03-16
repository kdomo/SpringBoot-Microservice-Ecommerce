package com.domo.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.domo.inventoryservice.model.Inventory;
import com.domo.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(InventoryRepository inventoryRepository) {
		return args -> {
			inventoryRepository.save(Inventory.builder().skuCode("iphone_13").quantity(100).build());
			inventoryRepository.save(Inventory.builder().skuCode("iphone_13_red").quantity(0).build());
		};
	}
}
