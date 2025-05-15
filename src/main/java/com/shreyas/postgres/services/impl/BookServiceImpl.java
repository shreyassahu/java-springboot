package com.shreyas.postgres.services.impl;

import com.shreyas.postgres.domain.dto.BookDto;
import com.shreyas.postgres.domain.entities.BookEntity;
import com.shreyas.postgres.mappers.Mapper;
import com.shreyas.postgres.repositories.BookRepository;
import com.shreyas.postgres.services.BookService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

  BookRepository bookRepository;
  Mapper<BookEntity, BookDto> bookMapper;

  public BookServiceImpl(BookRepository bookRepository, Mapper<BookEntity, BookDto> bookMapper) {
    this.bookRepository = bookRepository;
    this.bookMapper = bookMapper;
  }

  @Override
  public BookDto createBook(String isbn, BookDto bookDto) {
    BookEntity bookEntity = bookMapper.mapFrom(bookDto);
    BookEntity savedBookEntity = bookRepository.save(bookEntity);
    return bookMapper.mapTo(savedBookEntity);
  }

  @Override
  public List<BookDto> getBooks() {
    List<BookDto> bookDtoList = new ArrayList<>();
    Iterable<BookEntity> bookEntities = bookRepository.findAll();
    for (BookEntity bookEntity : bookEntities) {
      bookDtoList.add(bookMapper.mapTo(bookEntity));
    }
    return bookDtoList;
  }
}
