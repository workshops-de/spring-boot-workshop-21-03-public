package de.workshops.bookdemo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class BookRepository {

    @Autowired
    ObjectMapper mapper;
    
    private List<Book> books;
    

    
    @PostConstruct
    public void init() throws Exception {
        this.books = Arrays.asList(mapper.readValue(new File("src/main/resources/books.json"), Book[].class));
    }



    public List<Book> findAllBooks() {
        return this.books;
    }

}
