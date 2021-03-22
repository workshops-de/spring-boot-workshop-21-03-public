package de.workshops.bookdemo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerTest {

    @Autowired
    private BookRestController restController;
    
    @Autowired
    private MockMvc mockMvc;
    
    
    @Test
    void testWithMethodCall() {
        assertNotNull(restController);
        List<Book> books = restController.getAllBooks();
        assertNotNull(books);
        assertEquals(3, books.size());
    }
    
    @Test
    void testWithMockMvc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BookRestController.REQUEST_URL))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("Clean Code")));
    }

}
