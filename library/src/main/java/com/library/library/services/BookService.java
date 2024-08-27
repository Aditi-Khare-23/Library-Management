package com.library.library.services;

import com.library.library.model.Book;
import com.library.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static List<Book> list=new ArrayList<>();

    @Autowired
    BookRepository bookRepository;

    //get all books
    public List<Book> getAllBooks() {
//        bookRepository.findAll().forEach(Book::addM);
        return list;
    }

    //get single book by id
    public static Book getBookById(int id){
        Book book = null;
        try {
            book = list.stream().filter(e -> e.getId() == id).findFirst().get();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return book;
    }

    //adding the book
    public Book addBook(Book b){
        list.add(b);
//        Book resultBook = bookRepository.save(b);
        return b;
    }

    //delete book
    public ResponseEntity<?> deleteBook(int book_Id){
        Book b = bookRepository.findById(book_Id).get();
        try {
            bookRepository.delete(b);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//            return "true";
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            return "false";
        }
    }

    //update book
    public Book updateBook(Book book, int bookId) {
//        Optional<Book> bd = bookRepository.findById(bookId);
        Book book1 = bookRepository.findById(bookId).get();

        book1.setGenre(book.getGenre());
        book1.setTitle(book.getTitle());
        book1.setAuthor(book.getAuthor());
        book1.setCopies_available(book.getCopies_available());
        book1.setPublication(book.getPublication());
        book1.setPublication_year(book.getPublication_year());
        Book updatedBook = bookRepository.save(book1);

        System.out.println(updatedBook.getTitle());
        return updatedBook;

    }
}
