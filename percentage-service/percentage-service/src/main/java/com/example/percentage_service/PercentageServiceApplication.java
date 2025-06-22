package com.example.percentage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

@SpringBootApplication
public class PercentageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PercentageServiceApplication.class, args);
	}
}
