package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ecommerce"})
public class EcommerceApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}
}