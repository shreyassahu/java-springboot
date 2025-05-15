package com.shreyas.postgres.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.domain.entities.AuthorEntity;

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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @Autowired
  public AuthorControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
  }

  @Test
  public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    testAuthorA.setId(null);
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(authorJson))
            .andExpect(status().isCreated());
  }

  @Test
  public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    testAuthorA.setId(null);
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(testAuthorA.getName()))
            .andExpect(jsonPath("$.age").value(testAuthorA.getAge()));
  }

  @Test
  public void testThatGetAuthorSuccessfullyReturnsHttp200Ok() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
            .andExpect(status().isOk());
  }

  @Test
  public void testThatGetAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[0].name").value(testAuthorA.getName()))
            .andExpect(jsonPath("$[0].age").value(testAuthorA.getAge()));
  }
}
