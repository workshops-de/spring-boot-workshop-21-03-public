package de.workshops.bookdemo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/bookview")
public class BookController {

    @Autowired
    ObjectMapper mapper;
    
    private List<Book> books;
    
        
    @PostConstruct
    public void init() throws Exception {
        this.books = Arrays.asList(mapper.readValue(new File("src/main/resources/books.json"), Book[].class));
    }

    
    @GetMapping
    public String getBooklist(Model model) {
        model.addAttribute("books", this.books);
        return "booklist.html";
    }
}
