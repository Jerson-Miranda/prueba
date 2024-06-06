package com.evenjoin.diet_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DietMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietMsApplication.class, args);
	}

}
