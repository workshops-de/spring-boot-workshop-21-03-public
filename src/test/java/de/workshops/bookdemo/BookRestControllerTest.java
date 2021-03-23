package de.workshops.bookdemo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerTest {

    @Autowired
    private BookRestController restController;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    ObjectMapper mapper;
    
    @Mock
    BookRepository bookRepository;
    
    
    @BeforeEach
    public void setup() {
        //ReflectionTestUtils.setField(bookService, "bookRepository", bookRepository);
    }
    
//    @Test
//    void testWithMethodCall() {
//        assertNotNull(restController);
//        List<Book> books = restController.getAllBooks();
//        assertNotNull(books);
//        assertEquals(3, books.size());
//    }
    
    @Test
    void testWithMockMvc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BookRestController.REQUEST_URL))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("Clean Code")));
    }

    @Test
    void testWithMockMvcResult() throws Exception {
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get(BookRestController.REQUEST_URL))
                .andReturn();
        String jsonPayload = result.getResponse().getContentAsString();
        Book[] books = mapper.readValue(jsonPayload, Book[].class);
        List<Book> booklist = Arrays.asList(books);
        assertEquals(3,  booklist.size());
    }
    
    @Test
    public void testWithRestAssuredMockMvc() {
        RestAssuredMockMvc.standaloneSetup(restController);
        RestAssuredMockMvc.given().
             log().all().
        when().
             get("/book").
        then().
             log().all().
             statusCode(200).
             body("author[0]", equalTo("Erich Gamma"));
    }
    
    @Test
    void mockTestWithMethodCall() throws Exception {
        when(bookRepository.findAllBooks()).thenReturn(null);
        
        mockMvc.perform(MockMvcRequestBuilders.get(BookRestController.REQUEST_URL))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

//    @Test
//    public void testWithRestAssure() {
//        RestAssured.given().
//            log().all().
//        when().
//            get("/book").
//        then().
//            log().all().
//            statusCode(200).
//            body("author[0]", equalTo("Erich Gamma"));
//    }
    
    


}
