package com.shreyas.postgres.dao.impl;

import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.dao.AuthorDao;
import com.shreyas.postgres.domain.Author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorDaoImplIntegrationTests {


  private AuthorDao underTest;
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public AuthorDaoImplIntegrationTests(AuthorDao underTest, JdbcTemplate jdbcTemplate) {
    this.underTest = underTest;
    this.jdbcTemplate = jdbcTemplate;
  }

  @BeforeEach
  void cleanDatabase() {
    jdbcTemplate.execute("DELETE FROM books");
    jdbcTemplate.execute("DELETE FROM authors");
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthor(1L);
    underTest.create(author);
    Optional<Author> result = underTest.findOne(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    Author author1 = TestDataUtil.createTestAuthor(1L);
    Author author2 = TestDataUtil.createTestAuthor(2L);
    underTest.create(author1);
    underTest.create(author2);
    List<Author> result = underTest.find();
    assertThat(result).hasSize(2).containsExactly(author1, author2);
  }

  @Test
  public void testThatAuthorCanBeUpdated() {
    Author author = TestDataUtil.createTestAuthor(1L);
    underTest.create(author);
    Author updatedAuthor = TestDataUtil.createTestAuthor(1L, "Paul Coelho", 65);
    underTest.update(1L, updatedAuthor);
    Optional<Author> result = underTest.findOne(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(updatedAuthor);
  }

  @Test
  public void testThatAuthorCanBeDeleted() {
    Author author = TestDataUtil.createTestAuthor(1L);
    underTest.create(author);
    underTest.delete(author.getId());
    Optional<Author> result = underTest.findOne(author.getId());
    assertThat(result).isNotPresent();
  }
}
