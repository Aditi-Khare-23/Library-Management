package com.library.member.Members.model;

import jakarta.persistence.*;

import java.awt.print.Book;

@Entity
@Table(name="Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    private String name;
    private int book_id;
    private String email;
    private String address;
    private long phone_no;
    //private String returning;

    public Member(int id, String name, int book_id, String email, String address, long phone_no, String returning){
        this.id = id;
        this.name = name;
        this.book_id = book_id;
        this.email = email;
        this.address = address;
        this.phone_no = phone_no;
        //this.returning = returning;
    }
    public Member(){

    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getBook_id(){
        return book_id;
    }
    public void setBook_id(int book_id){
        this.book_id = book_id;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public long getPhone_no(){
        return phone_no;
    }
    public void setPhone_no(long phone_no){
        this.phone_no = phone_no;
    }
//    public String getReturning(){
//        return returning;
//    }
//    public void setReturning(String returning){
//        this.returning = returning;
//    }

}


