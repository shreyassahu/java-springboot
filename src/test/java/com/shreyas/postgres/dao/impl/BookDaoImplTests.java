package com.shreyas.postgres.dao.impl;

import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.domain.Book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {
  @Mock
  private JdbcTemplate jdbcTemplate;

  @InjectMocks
  private BookDaoImpl underTest;

  @Test
  public void testThatCreateBookGeneratesCorrectSql() {
    Book book = TestDataUtil.createTestBook("isbn123");
    underTest.create(book);

    verify(jdbcTemplate).update(
            eq("INSERT INTO books (isbn, title, author_id) VALUES(?, ?, ?)"),
            eq("isbn123"), eq("Test Book"), eq(1L)
    );
  }

  @Test
  public void testThatGetBookGeneratesCorrectSql() {
    underTest.findOne("isbn123");
    verify(jdbcTemplate).query(
            eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
            ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
            eq("isbn123"));
  }

  @Test
  public void testThatFindManyBooksGeneratesCorrectSql() {
    underTest.find();
    verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books"), ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
  }

  @Test
  public void testThatUpdateBookGeneratesCorrectSql() {
    Book book = TestDataUtil.createTestBook("isbn123");
    underTest.update("isbn123", book);
    verify(jdbcTemplate).update(eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"), eq(book.getIsbn()), eq(book.getTitle()), eq(book.getAuthorId()), eq("isbn123"));
  }

  @Test
  public void testThatDeleteBookGeneratesCorrectSql() {
    underTest.delete("isbn123");
    verify(jdbcTemplate).update(eq("DELETE FROM books WHERE isbn = ?"), eq("isbn123"));
  }
}
