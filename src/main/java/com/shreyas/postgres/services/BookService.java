package com.shreyas.postgres.services;

import com.shreyas.postgres.domain.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
  BookDto createBook(String isbn, BookDto bookDto);

  List<BookDto> getBooks();

  Optional<BookDto> getBookById(String isbn);

  BookDto patchUpdateBook(String isbn, BookDto bookDto);

  void deleteBook(String isbn);
}
