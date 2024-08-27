package com.library.library;

import com.library.library.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class LibraryApplication {

	@LoadBalanced
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LibraryApplication.class, args);

		BookRepository bookRepository = context.getBean(BookRepository.class);


	}

}
