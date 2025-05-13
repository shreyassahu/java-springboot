package com.shreyas.postgres.repositories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shreyas.postgres.TestDataUtil;
import com.shreyas.postgres.domain.entities.AuthorEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryIntegrationTests {

  private AuthorRepository underTest;

  @Autowired
  public AuthorEntityRepositoryIntegrationTests(AuthorRepository authorRepository) {
    this.underTest = authorRepository;
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    Optional<AuthorEntity> result = underTest.findById(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
  }


  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    AuthorEntity author1 = TestDataUtil.createTestAuthorA();
    underTest.save(author1);
    AuthorEntity author2 = TestDataUtil.createTestAuthorB();
    underTest.save(author2);
    Iterable<AuthorEntity> result = underTest.findAll();
    assertThat(result).hasSize(2).containsExactly(author1, author2);
  }

  @Test
  public void testThatAuthorCanBeUpdated() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    AuthorEntity savedAuthor = underTest.save(author);
    savedAuthor.setName("Shreyas Sahu");
    savedAuthor.setAge(24);
    underTest.save(savedAuthor);
    Optional<AuthorEntity> result = underTest.findById(savedAuthor.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(savedAuthor);
  }

  @Test
  public void testThatAuthorCanBeDeleted() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    underTest.deleteById(author.getId());
    Optional<AuthorEntity> result = underTest.findById(author.getId());
    assertThat(result).isNotPresent();
  }

  @Test
  public void testThatGetAuthorsWithAgeLessThan() {
    AuthorEntity authorA = TestDataUtil.createTestAuthorA();
    AuthorEntity savedAuthorA = underTest.save(authorA);
    AuthorEntity authorB = TestDataUtil.createTestAuthorB();
    AuthorEntity savedAuthorB = underTest.save(authorB);
    AuthorEntity authorC = TestDataUtil.createTestAuthorC();
    AuthorEntity savedAuthorC = underTest.save(authorC);
    Iterable<AuthorEntity> results = underTest.ageLessThan(50);
    assertThat(results).hasSize(2).containsExactly(savedAuthorB, savedAuthorC);
  }

  @Test
  public void testThatGetAuthorsWithAgeGreaterThan() {
    AuthorEntity authorA = TestDataUtil.createTestAuthorA();
    AuthorEntity savedAuthorA = underTest.save(authorA);
    AuthorEntity authorB = TestDataUtil.createTestAuthorB();
    AuthorEntity savedAuthorB = underTest.save(authorB);
    AuthorEntity authorC = TestDataUtil.createTestAuthorC();
    AuthorEntity savedAuthorC = underTest.save(authorC);
    Iterable<AuthorEntity> results = underTest.findAuthorsWithAgeGreaterThan(40);
    assertThat(results).hasSize(2).containsExactly(savedAuthorA, savedAuthorB);
  }
}
