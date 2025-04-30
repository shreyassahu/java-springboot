package com.shreyas.postgres.dao.impl;

import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.dao.AuthorDao;
import com.shreyas.postgres.dao.BookDao;
import com.shreyas.postgres.domain.Author;
import com.shreyas.postgres.domain.Book;

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
public class BookDaoImplIntegrationTests {


  private AuthorDao authorDao;
  private BookDao underTest;
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public BookDaoImplIntegrationTests(AuthorDao authorDao, BookDao underTest, JdbcTemplate jdbcTemplate) {
    this.authorDao = authorDao;
    this.underTest = underTest;
    this.jdbcTemplate = jdbcTemplate;
  }

  @BeforeEach
  void cleanDatabase() {
    jdbcTemplate.execute("DELETE FROM books");
    jdbcTemplate.execute("DELETE FROM authors");
  }

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthor(1L);
    Book book = TestDataUtil.createTestBook("isbn1");
    authorDao.create(author);
    underTest.create(book);
    Optional<Book> result = underTest.findOne(book.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {
    Author author1 = TestDataUtil.createTestAuthor(1L);
    Author author2 = TestDataUtil.createTestAuthor(2L);
    Book book1 = TestDataUtil.createTestBook("isbn1");
    Book book2 = TestDataUtil.createTestBook("isbn2");
    authorDao.create(author1);
    authorDao.create(author2);
    underTest.create(book1);
    underTest.create(book2);
    List<Book> result = underTest.find();
    assertThat(result).hasSize(2).containsExactly(book1, book2);
  }
}
