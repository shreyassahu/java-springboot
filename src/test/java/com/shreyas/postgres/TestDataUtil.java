package com.shreyas.postgres;

import com.shreyas.postgres.domain.Author;
import com.shreyas.postgres.domain.Book;

public final class TestDataUtil {
  private TestDataUtil() {
  }

  public static Author createTestAuthor(Long id) {
    return Author.builder().id(id).name("Abigail Rose").age(80).build();
  }

  public static Book createTestBook(String isbn) {
    return Book.builder().isbn(isbn).title("Test Book").authorId(1L).build();
  }
}
