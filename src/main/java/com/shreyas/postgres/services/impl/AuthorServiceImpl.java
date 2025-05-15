package com.shreyas.postgres.services.impl;

import com.shreyas.postgres.domain.dto.AuthorDto;
import com.shreyas.postgres.domain.entities.AuthorEntity;
import com.shreyas.postgres.mappers.Mapper;
import com.shreyas.postgres.repositories.AuthorRepository;
import com.shreyas.postgres.services.AuthorService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
  private final Mapper<AuthorEntity, AuthorDto> authorMapper;

  public AuthorServiceImpl(AuthorRepository authorRepository, Mapper<AuthorEntity, AuthorDto> authorMapper) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  @Override
  public AuthorDto createAuthor(AuthorDto authorDto) {
    AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
    AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
    return authorMapper.mapTo(savedAuthorEntity);
  }

  @Override
  public List<AuthorDto> getAuthors() {
    List<AuthorDto> authorDtoList = new ArrayList<>();
    Iterable<AuthorEntity> authorEntityIterable = authorRepository.findAll();
    for (AuthorEntity authorEntity : authorEntityIterable) {
      authorDtoList.add(authorMapper.mapTo(authorEntity));
    }
    return authorDtoList;
  }
}
