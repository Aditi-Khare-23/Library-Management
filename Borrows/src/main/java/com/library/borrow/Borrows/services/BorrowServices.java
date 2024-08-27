package com.library.borrow.Borrows.services;

import com.library.borrow.Borrows.model.Borrow;
import com.library.borrow.Borrows.repository.BorrowRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServices {
    private static List<Borrow> list = new ArrayList<>();

    @Autowired
    BorrowRepository repo;

    private final RestTemplate restTemplate;
    private final String bookServiceUrl = "http://LIBRARY";
    private final String memberServiceUrl = "http://MEMBERS";


    public BorrowServices(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "LIBRARY", fallbackMethod = "libraryServiceFallback")
    public boolean checkBookExists(int bookId){
        String url = bookServiceUrl + "/library-book-service/exists/books/" + bookId;
        System.out.println("Calling URL: " + url);

        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return response.getBody() != null && response.getBody();
    }

    @CircuitBreaker(name = "MEMBER", fallbackMethod = "memberServiceFallback")
    public boolean checkMemberExists(int memberId){
        String url = memberServiceUrl + "/library-member-service/exists/members/" + memberId;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return response.getBody() != null && response.getBody();
    }

    // Fallback method for library service
    public boolean libraryServiceFallback(int bookId, Throwable throwable) {
        System.out.println("Library service is currently unavailable. Please try again later.");
        return false;
    }

    // Fallback method for member service
    public boolean memberServiceFallback(int memberId, Throwable throwable) {
        System.out.println("Member service is currently unavailable. Please try again later.");
        return false;
    }

    public List<Borrow> getAll(){
        return list;
    }

    public Borrow addBorrow(Borrow b){
        return repo.save(b);
    }
    public static Borrow getById(int bor_id){
        Borrow bor = null;
        try {
            bor = list.stream().filter(e -> e.getId() == bor_id).findFirst().get();
        } catch (Exception e){
            e.printStackTrace();
        }
        return bor;
    }

    public ResponseEntity<?>deleteBorrow(int borrowId){
        try{
            repo.deleteById(borrowId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @CircuitBreaker(name = "LIBRARY", fallbackMethod = "libraryServiceFallback")
    public ResponseEntity<Borrow> updateBorrow(Borrow borrow, int borrowId){
        Borrow b = repo.findById(borrowId).get();
        b.setBook_id(borrow.getBook_id());
        b.setMember_id(borrow.getMember_id());
        b.setRent_date(borrow.getRent_date());
        b.setReturn_status(borrow.getReturn_status());
        //Borrow updatedBorrow = repo.save(b);

        int bookId = b.getBook_id();
        System.out.println(bookId);

        Optional<Borrow> optionalBorrow = repo.findById(borrowId);
        if (!optionalBorrow.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update copies_available based on return_status
        String operation = borrow.getReturn_status() ? "increment" : "decrement";
        String url = bookServiceUrl + "/library-book-service/update_copies/books/" + borrow.getBook_id() + "?operation=" + operation;
        try {
            restTemplate.put(url, null);
        } catch (RestClientException e) {
            e.printStackTrace();
            // Log or handle the exception
        }

        // Save the updated Borrow entity
        Borrow updatedborrow = repo.save(borrow);
        return ResponseEntity.ok(updatedborrow);
    }
    public ResponseEntity<Borrow> libraryServiceFallback(Borrow borrow, int borrowId, Throwable throwable) {
        // Handle fallback logic (e.g., log the error, return a partial response, etc.)
        System.out.println("Fallback triggered for library service: " + throwable.getMessage());
        // Return the updated borrow entity without updating the library service
        return ResponseEntity.ok(borrow);
    }

}
