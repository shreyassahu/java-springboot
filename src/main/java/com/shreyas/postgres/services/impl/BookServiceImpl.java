package com.shreyas.postgres.services.impl;

import com.shreyas.postgres.domain.dto.AuthorDto;
import com.shreyas.postgres.domain.dto.BookDto;
import com.shreyas.postgres.domain.entities.AuthorEntity;
import com.shreyas.postgres.domain.entities.BookEntity;
import com.shreyas.postgres.mappers.Mapper;
import com.shreyas.postgres.repositories.BookRepository;
import com.shreyas.postgres.services.BookService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

  BookRepository bookRepository;
  Mapper<BookEntity, BookDto> bookMapper;
  Mapper<AuthorEntity, AuthorDto> authorMapper;

  public BookServiceImpl(BookRepository bookRepository, Mapper<BookEntity, BookDto> bookMapper, Mapper<AuthorEntity, AuthorDto> authorMapper) {
    this.bookRepository = bookRepository;
    this.bookMapper = bookMapper;
    this.authorMapper = authorMapper;
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

  @Override
  public Optional<BookDto> getBookById(String isbn) {
    return bookRepository
            .findById(isbn)
            .map(bookMapper::mapTo);
  }

  @Override
  public BookDto patchUpdateBook(String isbn, BookDto bookDto) {
    bookDto.setIsbn(isbn);
    return bookRepository.findById(isbn).map(existingBookEntity -> {
      Optional.ofNullable(bookDto.getTitle()).ifPresent(existingBookEntity::setTitle);
      Optional.ofNullable(bookDto.getAuthorDto()).ifPresent(authorDto -> {
        existingBookEntity.setAuthorEntity(authorMapper.mapFrom(authorDto));
      });
      return bookMapper.mapTo(bookRepository.save(existingBookEntity));
    }).orElseThrow(() -> new RuntimeException("Author Doesn't exist"));
  }

  @Override
  public void deleteBook(String isbn) {
    bookRepository.deleteById(isbn);
  }
}
