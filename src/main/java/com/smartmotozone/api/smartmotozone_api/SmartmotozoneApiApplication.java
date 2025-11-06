package com.smartmotozone.api.smartmotozone_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SmartmotozoneApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartmotozoneApiApplication.class, args);
	}

}