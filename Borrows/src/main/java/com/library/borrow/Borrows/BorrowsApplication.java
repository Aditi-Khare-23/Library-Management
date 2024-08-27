package com.library.borrow.Borrows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
//import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableDiscoveryClient
public class BorrowsApplication {

//	@Bean
	@LoadBalanced
	public static void main(String[] args) {
		SpringApplication.run(BorrowsApplication.class, args);
	}

}
