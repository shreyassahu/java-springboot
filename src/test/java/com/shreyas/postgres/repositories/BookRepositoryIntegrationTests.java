package com.shreyas.postgres.repositories;
import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.domain.Author;
import com.shreyas.postgres.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

  private BookRepository underTest;

  @Autowired
  public BookRepositoryIntegrationTests(AuthorRepository authorRepository, BookRepository underTest) {
    this.underTest = underTest;
  }

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthorA();  // now with no .id()
    Book   book   = TestDataUtil.createTestBookA(author);
    Book saved = underTest.save(book);
    Optional<Book> fetched = underTest.findById(saved.getIsbn());
    assertThat(fetched).isPresent();
    assertThat(fetched.get()).isEqualTo(saved);
  }

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {
    Author author1 = TestDataUtil.createTestAuthorA();
    Author author2 = TestDataUtil.createTestAuthorB();
    Book book1 = TestDataUtil.createTestBookA(author1);
    Book book2 = TestDataUtil.createTestBookB(author2);
    Book savedBook1 = underTest.save(book1);
    Book savedBook2 = underTest.save(book2);
    Iterable<Book> result = underTest.findAll();
    assertThat(result).hasSize(2).containsExactly(savedBook1, savedBook2);
  }

  @Test
  public void testThatBookCanBeCreatedAndUpdated() {
    Author author = TestDataUtil.createTestAuthorA();
    Book book = TestDataUtil.createTestBookA(author);
    Book savedBook = underTest.save(book);
    savedBook.setTitle("Updated title");
    underTest.save(savedBook);
    Optional<Book> result = underTest.findById(savedBook.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(savedBook);
  }

  @Test
  public void testThatBookCanBeDeleted() {
    Author author = TestDataUtil.createTestAuthorA();
    Book book = TestDataUtil.createTestBookA(author);
    Book savedBook = underTest.save(book);
    underTest.deleteById(savedBook.getIsbn());
    Optional<Book> result = underTest.findById(savedBook.getIsbn());
    assertThat(result).isNotPresent();
  }
}
