package com.shreyas.postgres.services;

import com.shreyas.postgres.domain.dto.BookDto;

import java.util.List;

public interface BookService {
  BookDto createBook(String isbn, BookDto bookDto);

  List<BookDto> getBooks();
}
