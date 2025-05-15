package com.shreyas.postgres.mappers;

public interface Mapper<A, B> {
  B mapTo(A a);

  A mapFrom(B b);
}
