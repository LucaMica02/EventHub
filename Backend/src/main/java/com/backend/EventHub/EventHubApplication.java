package com.backend.EventHub;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class EventHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventHubApplication.class, args);
	}

}
