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

  @Test
  public void testThatGetAuthorByIdSuccessfullyReturnsHttp200Ok() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.get("/authors/1"))
            .andExpect(status().isOk());
  }

  @Test
  public void testThatGetAuthorByIdReturns404NotFoundIfNotExists() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/authors/1"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testThatGetAuthorByIdReturnsAuthorWhenExists() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.get("/authors/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(testAuthorA.getName()))
            .andExpect(jsonPath("$.age").value(testAuthorA.getAge()));
  }

  @Test
  public void testThatUpdateAuthorSuccessfullyReturnsHttp200Ok() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.put("/authors/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isOk());
  }

  @Test
  public void testThatUpdateAuthorReturns404NotFoundIfNotExists() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);

    mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testThatUpdateAuthorSuccessfullyReturnsUpdatedAuthor() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isCreated());

    testAuthorA.setName("test author");
    testAuthorA.setAge(20);
    String updatedAuthorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.put("/authors/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedAuthorJson))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(testAuthorA.getName()))
            .andExpect(jsonPath("$.age").value(testAuthorA.getAge()));
  }

  @Test
  public void testThatPatchAuthorSuccessfullyReturnsHttp200Ok() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isCreated());

    testAuthorA.setName("test patch author");
    testAuthorA.setAge(null);
    mockMvc.perform(MockMvcRequestBuilders.patch("/authors/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isOk());
  }

  @Test
  public void testThatPatchAuthorReturns404NotFoundIfNotExists() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);

    mockMvc.perform(MockMvcRequestBuilders.patch("/authors/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testThatPatchAuthorSuccessfullyReturnsUpdatedAuthor() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isCreated());

    testAuthorA.setName("test patch author");
    int originalAge = testAuthorA.getAge();
    testAuthorA.setAge(null);
    String updatedAuthorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.patch("/authors/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedAuthorJson))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(testAuthorA.getName()))
            .andExpect(jsonPath("$.age").value(originalAge));
  }

  @Test
  public void testThatDeleteAuthorSuccessfullyDeletesAndReturns204() throws Exception {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    String authorJson = objectMapper.writeValueAsString(testAuthorA);
    mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson))
            .andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.delete("/authors/1"))
            .andExpect(status().isNoContent());
  }

  @Test
  public void testThatDeleteAuthorReturns404NotFoundIfNotExists() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/authors/1"))
            .andExpect(status().isNotFound());
  }
}
