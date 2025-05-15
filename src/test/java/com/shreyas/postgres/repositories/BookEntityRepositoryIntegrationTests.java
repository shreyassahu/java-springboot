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
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();  // now with no .id()
    BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
    BookEntity saved = underTest.save(bookEntity);
    Optional<BookEntity> fetched = underTest.findById(saved.getIsbn());
    assertThat(fetched).isPresent();
    assertThat(fetched.get()).isEqualTo(saved);
  }

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {
    AuthorEntity authorEntity1 = TestDataUtil.createTestAuthorA();
    AuthorEntity authorEntity2 = TestDataUtil.createTestAuthorB();
    BookEntity bookEntity1 = TestDataUtil.createTestBookA(authorEntity1);
    BookEntity bookEntity2 = TestDataUtil.createTestBookB(authorEntity2);
    BookEntity savedBookEntity1 = underTest.save(bookEntity1);
    BookEntity savedBookEntity2 = underTest.save(bookEntity2);
    Iterable<BookEntity> result = underTest.findAll();
    assertThat(result).hasSize(2).containsExactly(savedBookEntity1, savedBookEntity2);
  }

  @Test
  public void testThatBookCanBeCreatedAndUpdated() {
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
    BookEntity savedBookEntity = underTest.save(bookEntity);
    savedBookEntity.setTitle("Updated title");
    underTest.save(savedBookEntity);
    Optional<BookEntity> result = underTest.findById(savedBookEntity.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(savedBookEntity);
  }

  @Test
  public void testThatBookCanBeDeleted() {
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
    BookEntity savedBookEntity = underTest.save(bookEntity);
    underTest.deleteById(savedBookEntity.getIsbn());
    Optional<BookEntity> result = underTest.findById(savedBookEntity.getIsbn());
    assertThat(result).isNotPresent();
  }
}
