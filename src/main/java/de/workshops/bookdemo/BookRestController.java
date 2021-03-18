package de.workshops.bookdemo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(BookRestController.REQUEST_URL)
public class BookRestController {

    static final String REQUEST_URL = "/book";

    @Autowired
    ObjectMapper mapper;
    
    private List<Book> books;
    
        
    @PostConstruct
    public void init() throws Exception {
        this.books = Arrays.asList(mapper.readValue(new File("src/main/resources/books.json"), Book[].class));
    }
    
    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }
}
