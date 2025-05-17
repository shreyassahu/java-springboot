package com.shreyas.postgres.services.impl;

import com.shreyas.postgres.domain.dto.AuthorDto;
import com.shreyas.postgres.domain.entities.AuthorEntity;
import com.shreyas.postgres.mappers.Mapper;
import com.shreyas.postgres.repositories.AuthorRepository;
import com.shreyas.postgres.services.AuthorService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
  private final Mapper<AuthorEntity, AuthorDto> authorMapper;

  public AuthorServiceImpl(AuthorRepository authorRepository, Mapper<AuthorEntity, AuthorDto> authorMapper) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  @Override
  public AuthorDto saveAuthor(AuthorDto authorDto) {
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

  @Override
  public Optional<AuthorDto> getAuthorById(Long id) {
    return authorRepository
            .findById(id)
            .map(authorMapper::mapTo);
  }

  @Override
  public AuthorDto patchUpdateAuthor(Long id, AuthorDto authorDto) {
    authorDto.setId(id);
    return authorRepository.findById(id).map(existingAuthor -> {
      Optional.ofNullable(authorDto.getName()).ifPresent(existingAuthor::setName);
      Optional.ofNullable(authorDto.getAge()).ifPresent(existingAuthor::setAge);
      return authorMapper.mapTo(authorRepository.save(existingAuthor));
    }).orElseThrow(() -> new RuntimeException("Author Doesn't exist"));
  }

  @Override
  public void deleteAuthor(Long id) {
    authorRepository.deleteById(id);
  }
}
