package com.shreyas.postgres.controllers;

import com.shreyas.postgres.domain.entities.AuthorEntity;
import com.shreyas.postgres.domain.dto.AuthorDto;
import com.shreyas.postgres.mappers.Mapper;
import com.shreyas.postgres.services.AuthorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

  private AuthorService authorService;

  private Mapper<AuthorEntity, AuthorDto> authorMapper;

  public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
  }

  @PostMapping(path = "/authors")
  public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
    return new ResponseEntity<>(authorService.saveAuthor(authorDto), HttpStatus.CREATED);
  }

  @GetMapping(path = "/authors")
  public ResponseEntity<List<AuthorDto>> getAuthors() {
    return new ResponseEntity<>(authorService.getAuthors(), HttpStatus.valueOf(200));
  }

  @GetMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
    Optional<AuthorDto> authorDtoOptional= authorService.getAuthorById(id);
    return authorDtoOptional.map(authorDto -> new ResponseEntity<>(authorDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
    if(authorService.getAuthorById(id).isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    authorDto.setId(id);
    return new ResponseEntity<>(authorService.saveAuthor(authorDto), HttpStatus.OK);
  }

  @PatchMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> patchAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
    if(authorService.getAuthorById(id).isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(authorService.patchUpdateAuthor(id, authorDto), HttpStatus.OK);
  }

  @DeleteMapping(path = "/authors/{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
    if(authorService.getAuthorById(id).isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    authorService.deleteAuthor(id);
    return ResponseEntity.noContent().build();
  }
}
