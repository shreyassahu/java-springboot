package com.shreyas.postgres.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.domain.dto.BookDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @Autowired
  public BookControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
  }

  @Test
  public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
    BookDto testBookDto = TestDataUtil.createTestBookDto(null);
    String bookJson = objectMapper.writeValueAsString(testBookDto);
    mockMvc.perform(MockMvcRequestBuilders.put("/books/{isbn}", testBookDto.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson))
            .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void testThatCreateBookSuccessfullyReturnsSavedAuthor() throws Exception {
    BookDto testBookDto = TestDataUtil.createTestBookDto(null);
    String bookJson = objectMapper.writeValueAsString(testBookDto);
    mockMvc.perform(MockMvcRequestBuilders.put("/books/{isbn}", testBookDto.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle()));
  }

  @Test
  public void testThatGetBooksSuccessfullyReturnsHttp200Ok() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/books"))
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testThatGetBooksSuccessfullyReturnsSavedBook() throws Exception {
    BookDto testBookDto = TestDataUtil.createTestBookDto(null);
    String bookJson = objectMapper.writeValueAsString(testBookDto);
    mockMvc.perform(MockMvcRequestBuilders.put("/books/{isbn}", testBookDto.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle()));

    mockMvc.perform(MockMvcRequestBuilders.get("/books"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].isbn").value(testBookDto.getIsbn()))
            .andExpect(jsonPath("$[0].title").value(testBookDto.getTitle()))
            .andExpect(jsonPath("$[0].author").value(testBookDto.getAuthorDto()));
  }
}
