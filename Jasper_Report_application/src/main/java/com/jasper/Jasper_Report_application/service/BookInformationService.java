package com.jasper.Jasper_Report_application.service;

import com.jasper.Jasper_Report_application.entities.BookInformation;
import com.jasper.Jasper_Report_application.repository.BookInformationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookInformationService {

    @Autowired
    private BookInformationRepo bookRepo;


    public List<BookInformation> getAllBooks(){
        return bookRepo.findAll();
    }

    public Optional<BookInformation> getBookById(int bookId){
        return bookRepo.findById(bookId);
    }

    public BookInformation saveBook(BookInformation book){
        return bookRepo.save(book);
    }

    public BookInformation updateBook(int bookId, BookInformation book){
            return bookRepo.findById(bookId).map(b -> {
            b.setBookName(book.getBookName());
            b.setAuthorName(book.getAuthorName());
            b.setPrice(book.getPrice());
            return bookRepo.save(b);
        }).orElseThrow(() -> new RuntimeException("Provided Book not found !"));
    }

    public void deleteBookById(int bookId){
        bookRepo.deleteById(bookId);
    }

    
}
