package com.shreyas.postgres.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
  private String isbn;
  private String title;
  @JsonProperty("author")
  private AuthorDto authorDto;
}
