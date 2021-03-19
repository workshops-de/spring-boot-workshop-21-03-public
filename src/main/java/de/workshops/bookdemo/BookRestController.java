package de.workshops.bookdemo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BookRestController.REQUEST_URL)
public class BookRestController {

    static final String REQUEST_URL = "/book";

    @Autowired
    private BookService bookService;
    
            
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.loadAllBooks();
    }
    
    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return getAllBooks().stream().filter(book -> hasIsbn(book, isbn)).findFirst().orElseThrow(() -> new BookException("no book for isbn " + isbn));
    }
    
//    @GetMapping(params = "author")
//    public Book getBookByAuthor(@RequestParam(required = false) String author) {
//        return getAllBooks().stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow();
//    }
    
    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody BookSearchRequest request) {
        return getAllBooks().stream().filter(book -> find(book, request)).collect(Collectors.toList());
    }
    
    private boolean find(Book book, BookSearchRequest request) {
        return hasIsbn(book, request.getIsbn()) && hasAuthor(book, request.getAuthor());
    }
    
    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }
    
    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().indexOf(author) != -1;
    }   
    
    @ExceptionHandler(BookException.class)
    //@ResponseBody
    public ResponseEntity<BookError> error(BookException ex) {
        //return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
        BookError error = new BookError();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }
    
    
}
