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
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    underTest.save(authorEntity);
    Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(authorEntity);
  }


  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    AuthorEntity authorEntity1 = TestDataUtil.createTestAuthorA();
    underTest.save(authorEntity1);
    AuthorEntity authorEntity2 = TestDataUtil.createTestAuthorB();
    underTest.save(authorEntity2);
    Iterable<AuthorEntity> result = underTest.findAll();
    assertThat(result).hasSize(2).containsExactly(authorEntity1, authorEntity2);
  }

  @Test
  public void testThatAuthorCanBeUpdated() {
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    AuthorEntity savedAuthorEntity = underTest.save(authorEntity);
    savedAuthorEntity.setName("Shreyas Sahu");
    savedAuthorEntity.setAge(24);
    underTest.save(savedAuthorEntity);
    Optional<AuthorEntity> result = underTest.findById(savedAuthorEntity.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(savedAuthorEntity);
  }

  @Test
  public void testThatAuthorCanBeDeleted() {
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    underTest.save(authorEntity);
    underTest.deleteById(authorEntity.getId());
    Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
    assertThat(result).isNotPresent();
  }

  @Test
  public void testThatGetAuthorsWithAgeLessThan() {
    AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
    AuthorEntity savedAuthorEntityA = underTest.save(authorEntityA);
    AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
    AuthorEntity savedAuthorEntityB = underTest.save(authorEntityB);
    AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
    AuthorEntity savedAuthorEntityC = underTest.save(authorEntityC);
    Iterable<AuthorEntity> results = underTest.ageLessThan(50);
    assertThat(results).hasSize(2).containsExactly(savedAuthorEntityB, savedAuthorEntityC);
  }

  @Test
  public void testThatGetAuthorsWithAgeGreaterThan() {
    AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
    AuthorEntity savedAuthorEntityA = underTest.save(authorEntityA);
    AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
    AuthorEntity savedAuthorEntityB = underTest.save(authorEntityB);
    AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
    AuthorEntity savedAuthorEntityC = underTest.save(authorEntityC);
    Iterable<AuthorEntity> results = underTest.findAuthorsWithAgeGreaterThan(40);
    assertThat(results).hasSize(2).containsExactly(savedAuthorEntityA, savedAuthorEntityB);
  }
}
