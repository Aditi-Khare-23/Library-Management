package com.library.borrow.Borrows.controller;

import ch.qos.logback.classic.Logger;
import com.library.borrow.Borrows.model.Borrow;
import com.library.borrow.Borrows.repository.BorrowRepository;
import com.library.borrow.Borrows.services.BorrowServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library-borrow-service")
public class BorrowController {

    @Autowired
    BorrowRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    private final String bookServiceUrl = "http://LIBRARY";
    @Autowired
    private BorrowServices borrowServices;
    private Logger logger;
//    private Object BorrowServices;
//    private Object BorrowServices;

//    private BorrowServices;

    @GetMapping("/get/borrows")
    public ResponseEntity<List<Borrow>> getAll(){
        List<Borrow> list = repo.findAll();
        if (list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(list));
    }

    @GetMapping("/get/borrows/{id}")
    public ResponseEntity<Borrow> getById(@PathVariable("id") int id){
        Borrow borrow = repo.findById(id).get();
        if (borrow == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.of(Optional.of(borrow));
    }

    @PostMapping("/save/borrows")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Borrow>addBorrow(@RequestBody Borrow borrow){
        Borrow b = null;
        int bookId = borrow.getBook_id();
        int memberId = borrow.getMember_id();

        boolean isBookValid = borrowServices.checkBookExists(bookId);
        boolean isMemberValid = borrowServices.checkMemberExists(memberId);

        try {
            if (!isBookValid)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            if (!isMemberValid)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            String operation = borrow.getReturn_status() ? "increment" : "decrement";
            String url = bookServiceUrl + "/library-book-service/update_copies/books/" + bookId + "?operation=" + operation;
            restTemplate.put(url, null);

            // Save the Borrow entity to the database
            b = this.borrowServices.addBorrow(borrow);
            //Borrow savedBorrow = repo.save(borrow);
            return ResponseEntity.status(HttpStatus.CREATED).body(b);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/remove/borrows/{id}")
    public ResponseEntity<?> deleteBorrow(@PathVariable("id") int borrowId){
        try {
            return this.borrowServices.deleteBorrow(borrowId);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PutMapping("/update/borrows/{id}")
    public ResponseEntity<Borrow> updateBorrow(@RequestBody Borrow borrow, @PathVariable("id") int bid){
        int bookId = borrow.getBook_id();
        int memberId = borrow.getMember_id();

        boolean isBookValid = borrowServices.checkBookExists(bookId);
        boolean isMemberValid = borrowServices.checkMemberExists(memberId);
        Borrow existingEntity = repo.findById(borrow.getId()).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        if (existingEntity.getId() != (borrow.getId())) {
            throw new IllegalArgumentException("Primary key cannot be updated.");
        }
        try {
            if (!isBookValid)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            if (!isMemberValid)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            Boolean o = borrow.getReturn_status();
            this.borrowServices.updateBorrow(borrow, bid);
            return ResponseEntity.ok().body(borrow);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
