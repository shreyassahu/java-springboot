package com.shreyas.postgres;

import com.shreyas.postgres.domain.dto.AuthorDto;
import com.shreyas.postgres.domain.dto.BookDto;
import com.shreyas.postgres.domain.entities.AuthorEntity;
import com.shreyas.postgres.domain.entities.BookEntity;

public final class TestDataUtil {
  private TestDataUtil(){
  }

  public static AuthorEntity createTestAuthorA() {
    return AuthorEntity.builder()
            .name("Abigail Rose")
            .age(80)
            .build();
  }

  public static AuthorEntity createTestAuthorB() {
    return AuthorEntity.builder()
            .name("Thomas Cronin")
            .age(44)
            .build();
  }

  public static AuthorEntity createTestAuthorC() {
    return AuthorEntity.builder()
            .name("Jesse A Casey")
            .age(24)
            .build();
  }

  public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
    return BookEntity.builder()
            .isbn("978-1-2345-6789-0")
            .title("The Shadow in the Attic")
            .authorEntity(authorEntity)
            .build();
  }

  public static BookDto createTestBookDto(final AuthorDto authorDto) {
    return BookDto.builder()
            .isbn("978-1-2345-6789-0")
            .title("The Shadow in the Attic")
            .authorDto(authorDto)
            .build();
  }

  public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
    return BookEntity.builder()
            .isbn("978-1-2345-6789-1")
            .title("Beyond the Horizon")
            .authorEntity(authorEntity)
            .build();
  }

  public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
    return BookEntity.builder()
            .isbn("978-1-2345-6789-2")
            .title("The Last Ember")
            .authorEntity(authorEntity)
            .build();
  }
}