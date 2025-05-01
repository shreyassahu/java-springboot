package com.shreyas.postgres;

import com.shreyas.postgres.domain.Author;
import com.shreyas.postgres.domain.Book;

public final class TestDataUtil {
  private TestDataUtil() {
  }

  public static Author createTestAuthor(Long id) {
    return createTestAuthor(id, "Abigail Rose", 80);
  }

  public static Author createTestAuthor(Long id, String name, Integer age) {
    return Author.builder().id(id).name(name).age(age).build();
  }

  public static Book createTestBook(String isbn) {
    return createTestBook(isbn, "Test Book", 1L);
  }

  public static Book createTestBook(String isbn, String title, Long authorId) {
    return Book.builder().isbn(isbn).title(title).authorId(authorId).build();
  }
}
