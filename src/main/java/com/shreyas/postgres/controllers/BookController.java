package com.shreyas.postgres.controllers;

import com.shreyas.postgres.domain.dto.BookDto;
import com.shreyas.postgres.services.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

  private BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PutMapping("/books/{isbn}")
  public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
    bookDto.setIsbn(isbn);
    return new ResponseEntity<>(bookService.createBook(isbn, bookDto), HttpStatus.CREATED);
  }

  @GetMapping("/books")
  public ResponseEntity<List<BookDto>> getAllBooks() {
    return new ResponseEntity<>(bookService.getBooks(), HttpStatus.valueOf(200));
  }
}
