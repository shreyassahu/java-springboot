package com.shreyas.postgres.controllers;

import com.shreyas.postgres.domain.entities.AuthorEntity;
import com.shreyas.postgres.domain.dto.AuthorDto;
import com.shreyas.postgres.mappers.Mapper;
import com.shreyas.postgres.services.AuthorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    return new ResponseEntity<>(authorService.createAuthor(authorDto), HttpStatus.CREATED);
  }

  @GetMapping(path = "/authors")
  public ResponseEntity<List<AuthorDto>> getAuthors() {
    return new ResponseEntity<>(authorService.getAuthors(), HttpStatus.valueOf(200));
  }
}
