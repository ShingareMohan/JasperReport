package com.jasper.Jasper_Report_application.service;

import com.jasper.Jasper_Report_application.entities.BookInformation;
import com.jasper.Jasper_Report_application.repository.BookInformationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookInformationServiceTest {

    @Mock
    private BookInformationRepo bookRepo;

    @InjectMocks
    private BookInformationService bookService;

    private BookInformation book1;
    private BookInformation book2;

    @BeforeEach
    void setUp() {
        book1 = new BookInformation(1, "Java Basics", "John Doe", 20.99);
        book2 = new BookInformation(2, "Spring Boot Guide", "Jane Doe", 35.99);
    }

    @Test
    void testGetAllBooks() {
        List<BookInformation> bookList = Arrays.asList(book1, book2);
        when(bookRepo.findAll()).thenReturn(bookList);

        List<BookInformation> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Java Basics", result.get(0).getBookName());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    void testGetBookById_WhenBookExists() {
        when(bookRepo.findById(1)).thenReturn(Optional.of(book1));

        Optional<BookInformation> result = bookService.getBookById(1);

        assertTrue(result.isPresent());
        assertEquals("Java Basics", result.get().getBookName());
        verify(bookRepo, times(1)).findById(1);
    }

    @Test
    void testGetBookById_WhenBookDoesNotExist() {
        when(bookRepo.findById(3)).thenReturn(Optional.empty());

        Optional<BookInformation> result = bookService.getBookById(3);

        assertFalse(result.isPresent());
        verify(bookRepo, times(1)).findById(3);
    }

    @Test
    void testSaveBook() {
        when(bookRepo.save(book1)).thenReturn(book1);

        BookInformation savedBook = bookService.saveBook(book1);

        assertNotNull(savedBook);
        assertEquals("Java Basics", savedBook.getBookName());
        verify(bookRepo, times(1)).save(book1);
    }

    @Test
    void testUpdateBook_WhenBookExists() {
        when(bookRepo.findById(1)).thenReturn(Optional.of(book1));
        when(bookRepo.save(any(BookInformation.class))).thenReturn(book1);

        BookInformation updatedBook = new BookInformation(1, "Java Advanced", "John Doe", 25.99);
        BookInformation result = bookService.updateBook(1, updatedBook);

        assertEquals("Java Advanced", result.getBookName());
        assertEquals(25.99, result.getPrice());
        verify(bookRepo, times(1)).findById(1);
        verify(bookRepo, times(1)).save(any(BookInformation.class));
    }

    @Test
    void testUpdateBook_WhenBookDoesNotExist() {
        when(bookRepo.findById(3)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.updateBook(3, book1);
        });

        assertEquals("Provided Book not found !", exception.getMessage());
        verify(bookRepo, times(1)).findById(3);
        verify(bookRepo, never()).save(any(BookInformation.class));
    }

    @Test
    void testDeleteBookById() {
        doNothing().when(bookRepo).deleteById(1);

        bookService.deleteBookById(1);

        verify(bookRepo, times(1)).deleteById(1);
    }
}
