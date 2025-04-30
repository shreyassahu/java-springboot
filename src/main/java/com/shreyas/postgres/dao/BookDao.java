package com.shreyas.postgres.dao;

import com.shreyas.postgres.domain.Book;
import java.util.Optional;
import java.util.List;

public interface BookDao {
  void create(Book book);

  Optional<Book> findOne(String isbn123);

  List<Book> find();
}
