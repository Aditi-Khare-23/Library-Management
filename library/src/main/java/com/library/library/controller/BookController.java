package com.library.library.controller;

import com.library.library.model.Book;
import com.library.library.repository.BookRepository;
import com.library.library.services.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library-book-service")
public class BookController {

    @Autowired
    BookRepository repo;

    @Autowired
    private BookService bookService;

    //get all book handler
    @GetMapping("/get/books")
    public ResponseEntity<List<Book>> getBooks(){

        //List<Book> list = bookService.getAllBooks();
        List<Book> list = repo.findAll();
        if (list.size() < 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.of(Optional.of(list));

    }

    //get single book handler
    @GetMapping("/get/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") int id){
        //Book book = BookService.getBookById(id);
        Book book = repo.findById(id).get();
        if (book == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.of(Optional.of(book));
    }

    @GetMapping("/exists/books/{id}")
    public ResponseEntity<Boolean> checkBookExists(@PathVariable("id") int bookId){
        boolean exists = repo.existsById(bookId);
        return ResponseEntity.ok(exists);
    }

    //new book handler
    @PostMapping("/save/books")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book b = null;
        try{
            b = this.bookService.addBook(book);
            System.out.println(book);
            //repo.save(b);
            return ResponseEntity.of(Optional.of(b));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/remove/books/{bookId}")
    //delete book handler
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") int bookId){
//        Book b = repo.findById(bookId).get();
        try {
            return this.bookService.deleteBook(bookId);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/books/{bookId}")
    //update book handler
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("bookId") int bookId){
//        book = repo.findById(bookId).get();
        Book existingEntity = repo.findById(book.getId()).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        if (existingEntity.getId() != (book.getId())) {
            throw new IllegalArgumentException("Primary key cannot be updated.");
        }
        try {
            this.bookService.updateBook(book, bookId);
            return ResponseEntity.ok().body(book);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update_copies/books/{id}")
    public ResponseEntity<Book> updateCopiesAvailable (@PathVariable("id") int id, @RequestParam String operation){
        Optional<Book> optionalBook = repo.findById(id);
        if (!optionalBook.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Book book = optionalBook.get();
        if ("increment".equalsIgnoreCase(operation)) {
            book.setCopies_available(book.getCopies_available() + 1);
        } else if ("decrement".equalsIgnoreCase(operation)) {
            book.setCopies_available(book.getCopies_available() - 1);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        repo.save(book);
        return ResponseEntity.ok(book);
    }

}
