package com.matela.production;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProductionApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductionApplication.class, args);

	}

}
