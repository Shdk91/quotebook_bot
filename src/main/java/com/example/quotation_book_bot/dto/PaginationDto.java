package com.example.quotation_book_bot.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationDto {

  Long currentPage;
  Long nextPage;
  Long totalPages;

  public PaginationDto() {
  }

  public PaginationDto(Long currentPage, Long nextPage, Long totalPages) {
    this.currentPage = currentPage;
    this.nextPage = nextPage;
    this.totalPages = totalPages;
  }

  @Override
  public String toString() {
    return "PaginationDto{" +
        "currentPage=" + currentPage +
        ", nextPage=" + nextPage +
        ", totalPages=" + totalPages +
        '}';
  }
}
