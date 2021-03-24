package de.workshops.bookdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookDBTest {

    @Autowired
    BookRepositoryJpa repository;

    @Test
    void testCreate() throws Exception {
        Book book = Book.builder()
                .title("Titel")
                .author("Autor")
                .description("Beschreibung")
                .isbn("1234567890")
                .build();
        repository.save(book);
        
        List<Book> books = new ArrayList<>();
        repository.findAll().forEach(books::add);
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }
    
    @Test
    void testFindByAuthor() {
        List<Book> books = repository.findByAuthor("Autor");
        assertNotNull(books);
        assertEquals(2, books.size());
    }
}
