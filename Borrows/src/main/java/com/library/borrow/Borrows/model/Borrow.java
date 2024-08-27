package com.library.borrow.Borrows.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table
public class Borrow {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int book_id;
    private int member_id;
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String rent_date;
    private boolean return_status;

    public Borrow(int id, int book_id,int member_id,String rent_date, boolean return_status){
        this.id = id;
        this.book_id = book_id;
        this.member_id = member_id;
        this.rent_date = rent_date;
        this.return_status = return_status;
    }
    public Borrow(){

    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getBook_id(){
        return book_id;
    }
    public void setBook_id(int book_id){
        this.book_id = book_id;
    }
    public int getMember_id(){
        return member_id;
    }
    public void setMember_id(int member_id){
        this.member_id = member_id;
    }
    public String getRent_date(){
        return rent_date;
    }
    public void setRent_date(String rent_date){
        this.rent_date = rent_date;
    }
    public boolean getReturn_status(){
        return return_status;
    }
    public void setReturn_status(boolean return_status){
        this.return_status = return_status;
    }
}
