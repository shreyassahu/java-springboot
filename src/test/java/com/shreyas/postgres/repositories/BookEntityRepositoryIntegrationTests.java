package com.shreyas.postgres.repositories;
import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.domain.entities.AuthorEntity;
import com.shreyas.postgres.domain.entities.BookEntity;
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
public class BookEntityRepositoryIntegrationTests {

  private BookRepository underTest;

  @Autowired
  public BookEntityRepositoryIntegrationTests(AuthorRepository authorRepository, BookRepository underTest) {
    this.underTest = underTest;
  }

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();  // now with no .id()
    BookEntity book   = TestDataUtil.createTestBookA(author);
    BookEntity saved = underTest.save(book);
    Optional<BookEntity> fetched = underTest.findById(saved.getIsbn());
    assertThat(fetched).isPresent();
    assertThat(fetched.get()).isEqualTo(saved);
  }

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {
    AuthorEntity author1 = TestDataUtil.createTestAuthorA();
    AuthorEntity author2 = TestDataUtil.createTestAuthorB();
    BookEntity book1 = TestDataUtil.createTestBookA(author1);
    BookEntity book2 = TestDataUtil.createTestBookB(author2);
    BookEntity savedBook1 = underTest.save(book1);
    BookEntity savedBook2 = underTest.save(book2);
    Iterable<BookEntity> result = underTest.findAll();
    assertThat(result).hasSize(2).containsExactly(savedBook1, savedBook2);
  }

  @Test
  public void testThatBookCanBeCreatedAndUpdated() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    BookEntity book = TestDataUtil.createTestBookA(author);
    BookEntity savedBook = underTest.save(book);
    savedBook.setTitle("Updated title");
    underTest.save(savedBook);
    Optional<BookEntity> result = underTest.findById(savedBook.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(savedBook);
  }

  @Test
  public void testThatBookCanBeDeleted() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    BookEntity book = TestDataUtil.createTestBookA(author);
    BookEntity savedBook = underTest.save(book);
    underTest.deleteById(savedBook.getIsbn());
    Optional<BookEntity> result = underTest.findById(savedBook.getIsbn());
    assertThat(result).isNotPresent();
  }
}
