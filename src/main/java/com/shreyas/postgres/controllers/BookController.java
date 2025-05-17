package com.shreyas.postgres.controllers;

import com.shreyas.postgres.domain.dto.BookDto;
import com.shreyas.postgres.services.BookService;
import com.shreyas.postgres.services.impl.AuthorServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

  private final AuthorServiceImpl authorServiceImpl;
  private BookService bookService;

  public BookController(BookService bookService, AuthorServiceImpl authorServiceImpl) {
    this.bookService = bookService;
    this.authorServiceImpl = authorServiceImpl;
  }

  @PutMapping("/books/{isbn}")
  public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
    bookDto.setIsbn(isbn);
    if(bookService.getBookById(isbn).isEmpty()) {
      return new ResponseEntity<>(bookService.createBook(isbn, bookDto), HttpStatus.CREATED);
    }
    return new ResponseEntity<>(bookService.createBook(isbn, bookDto), HttpStatus.OK);
  }

  @GetMapping("/books")
  public ResponseEntity<List<BookDto>> getAllBooks() {
    return new ResponseEntity<>(bookService.getBooks(), HttpStatus.valueOf(200));
  }

  @GetMapping("/books/{isbn}")
  public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
    Optional<BookDto> bookDtoOptional= bookService.getBookById(isbn);
    return bookDtoOptional.map(bookDto -> new ResponseEntity<>(bookDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PatchMapping("/books/{isbn}")
  public ResponseEntity<BookDto> patchUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
    if(bookService.getBookById(isbn).isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(bookService.patchUpdateBook(isbn, bookDto), HttpStatus.OK);
  }

  @DeleteMapping("/books/{isbn}")
  public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
    if(bookService.getBookById(isbn).isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    bookService.deleteBook(isbn);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
