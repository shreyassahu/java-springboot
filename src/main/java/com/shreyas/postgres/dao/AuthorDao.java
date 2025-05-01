package com.shreyas.postgres.dao;

import com.shreyas.postgres.domain.Author;

import java.util.Optional;

import java.util.List;

public interface AuthorDao {
  void create(Author author);

  Optional<Author> findOne(long l);

  List<Author> find();

  void update(long id, Author author);
}
