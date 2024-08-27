package com.library.library.model;

import jakarta.persistence.*;
import org.hibernate.dialect.MySQLDialect;

@Entity
@Table(name="Book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    private String title;
    private String author;
    private String genre;
    private String publication;
    private int publication_year;
    private int copies_available;

    public Book(int id, String title, String author, String genre, String publication, int publication_year, int copies_available){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publication = publication;
        this.publication_year = publication_year;
        this.copies_available = copies_available;
    }
    public Book(){

    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }
    public String getPublication(){
        return publication;
    }
    public void setPublication(String publication){
        this.publication = publication;
    }
    public int getPublication_year(){
        return publication_year;
    }
    public void setPublication_year(int publication_year){
        this.publication_year = publication_year;
    }
    public int getCopies_available(){
        return copies_available;
    }
    public void setCopies_available(int copies_available){
        this.copies_available = copies_available;
    }

//    @Override
    public String to_String(){
        return "Book [author = "+author+", id = "+id+", title = "+title+", genre = "+genre+", publication = "+publication+", publication_year = "+publication_year+", copies_available = "+copies_available+" ]";
    }

}
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name="books")
//
//public class Book {
//
//    private long id;
//
//    @Column(name = "title")
//    private String title;
//
//    @Column(name = "author")
//    private String author;
//
//    @Column(name = "genre")
//    private String genre;
//
//    @Column(name = "publisher")
//    private String publisher;
//
//    @Column(name = "publication_year")
//    private int publication_year;
//
//    @Column(name = "copies_available")
//    private int copies_available;
//
//}
//public Book(){
//
//}
