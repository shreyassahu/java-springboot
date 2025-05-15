package com.shreyas.postgres.services;

import com.shreyas.postgres.domain.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
  AuthorDto createAuthor(AuthorDto authorDto);

  List<AuthorDto> getAuthors();
}
