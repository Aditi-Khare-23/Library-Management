package com.library.member.Members;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.library.member.Members.Config;
import com.library.member.Members.model.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import library.src.main.java.com.library.library.model.Book;
import java.util.List;
import java.awt.print.Book;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class MembersApplication {


	@LoadBalanced
	public static void main(String[] args) {

		SpringApplication.run(MembersApplication.class, args);
		//useExchangeMethodsOfRestTemplate();

	}

//	private static void useExchangeMethodsOfRestTemplate(){
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
//
//		getSingleBookByExchangeMethod(requestEntity);
//		//getListOfBookByExchangeMethod(requestEntity);
//		//updateCopiesAvailable();
//
//	}
//
//	private static void getListOfBookByExchangeMethod(HttpEntity<Object> requestEntity){
//		ResponseEntity<List> responseEntity = restTemplate.exchange( baseUrl + "/get/books",
//				HttpMethod.GET,
//				requestEntity,
//				List.class);
//		HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
//		System.out.println("status code - "+statusCode);
//		List books = responseEntity.getBody();
//		System.out.println("response body - "+books);
//
//		HttpHeaders responseHeaders = responseEntity.getHeaders();
//		System.out.println("response headers - "+responseHeaders);
//
////		ResponseEntity<Member> responseBook = restTemplate.exchange(baseUrl + "/get/books",
////				HttpMethod.GET,
////				requestEntity,
////				Member.class);
////		Member bookBody = responseBook.getBody();
////		System.out.println("book object " + bookBody);
//	}
//
//	private static void getSingleBookByExchangeMethod(HttpEntity<Object> requestEntity){
//		ResponseEntity<String> responseEntity = restTemplate.exchange( baseUrl + "/get/books/12340",
//				HttpMethod.GET,
//				requestEntity,
//				String.class);
//		HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
//		System.out.println("status code - "+statusCode);
//		String book1 = responseEntity.getBody();
//		System.out.println("response body - "+book1);
//
//		HttpHeaders responseHeaders = responseEntity.getHeaders();
//		System.out.println("response headers - "+responseHeaders);
//
////		ResponseEntity<Member> responseBook = restTemplate.exchange(baseUrl + "/get/books",
////				HttpMethod.GET,
////				requestEntity,
////				Member.class);
////		Book bookBody = responseBook.getBody();
////		System.out.println("book object " + bookBody);
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			JsonNode rootNode = objectMapper.readTree(book1);
//			// Extract the specific field (e.g., copiesAvailable)
//			int copiesAvailable = rootNode.path("copies_available").asInt();
//			System.out.println("Copies available - " + copiesAvailable);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	private static void updateCopiesAvailable() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		// Step 1: Fetch the current value of copies_available
//		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
//		ResponseEntity<String> responseEntity = restTemplate.exchange(
//				baseUrl + "/get/books/12340",
//				HttpMethod.GET,
//				requestEntity,
//				String.class
//		);
//
//		String bookJson = responseEntity.getBody();
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			JsonNode rootNode = objectMapper.readTree(bookJson);
//			int copiesAvailable = rootNode.path("copies_available").asInt();
//			System.out.println("Current copies available - " + copiesAvailable);
//
//			// Step 2: Increment the value
//			copiesAvailable += 1;  // Increment by 1, or any other logic you need
//
//			// Step 3: Create the updated Book object
//			((ObjectNode) rootNode).put("copies_available", copiesAvailable);
//			String updatedBookJson = objectMapper.writeValueAsString(rootNode);
//
//			// Send a PUT request to update the value in the Book service
//			HttpEntity<String> putRequestEntity = new HttpEntity<>(updatedBookJson, headers);
//			ResponseEntity<String> putResponseEntity = restTemplate.exchange(
//					baseUrl + "/update/books/12340",
//					HttpMethod.PUT,
//					putRequestEntity,
//					String.class
//			);
//
//			System.out.println("Update response - " + putResponseEntity.getBody());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
