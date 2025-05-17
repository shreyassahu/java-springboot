package com.shreyas.postgres.services;

import com.shreyas.postgres.domain.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
  AuthorDto saveAuthor(AuthorDto authorDto);

  List<AuthorDto> getAuthors();

  Optional<AuthorDto> getAuthorById(Long id);

  AuthorDto patchUpdateAuthor(Long id, AuthorDto authorDto);

  void deleteAuthor(Long id);
}
