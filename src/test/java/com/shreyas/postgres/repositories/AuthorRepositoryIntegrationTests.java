package com.shreyas.postgres.repositories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

  private AuthorRepository underTest;

  @Autowired
  public AuthorRepositoryIntegrationTests(AuthorRepository authorRepository) {
    this.underTest = authorRepository;
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    Optional<Author> result = underTest.findById(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
  }


  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    Author author1 = TestDataUtil.createTestAuthorA();
    underTest.save(author1);
    Author author2 = TestDataUtil.createTestAuthorB();
    underTest.save(author2);
    Iterable<Author> result = underTest.findAll();
    assertThat(result).hasSize(2).containsExactly(author1, author2);
  }

  @Test
  public void testThatAuthorCanBeUpdated() {
    Author author = TestDataUtil.createTestAuthorA();
    Author savedAuthor = underTest.save(author);
    savedAuthor.setName("Shreyas Sahu");
    savedAuthor.setAge(24);
    underTest.save(savedAuthor);
    Optional<Author> result = underTest.findById(savedAuthor.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(savedAuthor);
  }

  @Test
  public void testThatAuthorCanBeDeleted() {
    Author author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    underTest.deleteById(author.getId());
    Optional<Author> result = underTest.findById(author.getId());
    assertThat(result).isNotPresent();
  }

  @Test
  public void testThatGetAuthorsWithAgeLessThan() {
    Author authorA = TestDataUtil.createTestAuthorA();
    Author savedAuthorA = underTest.save(authorA);
    Author authorB = TestDataUtil.createTestAuthorB();
    Author savedAuthorB = underTest.save(authorB);
    Author authorC = TestDataUtil.createTestAuthorC();
    Author savedAuthorC = underTest.save(authorC);
    Iterable<Author> results = underTest.ageLessThan(50);
    assertThat(results).hasSize(2).containsExactly(savedAuthorB, savedAuthorC);
  }

  @Test
  public void testThatGetAuthorsWithAgeGreaterThan() {
    Author authorA = TestDataUtil.createTestAuthorA();
    Author savedAuthorA = underTest.save(authorA);
    Author authorB = TestDataUtil.createTestAuthorB();
    Author savedAuthorB = underTest.save(authorB);
    Author authorC = TestDataUtil.createTestAuthorC();
    Author savedAuthorC = underTest.save(authorC);
    Iterable<Author> results = underTest.findAuthorsWithAgeGreaterThan(40);
    assertThat(results).hasSize(2).containsExactly(savedAuthorA, savedAuthorB);
  }
}
