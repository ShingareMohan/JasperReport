package com.jasper.Jasper_Report_application.controller;

import com.jasper.Jasper_Report_application.entities.BookInformation;
import com.jasper.Jasper_Report_application.service.BookInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookInformation")
public class BookInformationController {

    @Autowired
    private BookInformationService bookService;

    @GetMapping
    public List<BookInformation> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookInformation> getBookById(@PathVariable int id){
        Optional<BookInformation> bookById = bookService.getBookById(id);
        return bookById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public BookInformation createNewBookInfo(@RequestBody BookInformation book){
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookInformation> updateBook(@PathVariable int id, @RequestBody BookInformation book) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, book));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteBook(@PathVariable int id) {
            bookService.deleteBookById(id);
            return ResponseEntity.noContent().build();
        }
    }

