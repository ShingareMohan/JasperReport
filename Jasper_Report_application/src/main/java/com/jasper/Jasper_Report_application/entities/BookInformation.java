package com.jasper.Jasper_Report_application.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "book_information")
public class BookInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String bookName;
    private String authorName;
    private double price;

    public BookInformation() {
    }

    public BookInformation(int bookId, String bookName, String authorName, double price) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.price = price;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
