package com.example.quotation_book_bot.dto;

import com.example.quotation_book_bot.entity.Quote;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuoteDto {

  String message;
  PaginationDto pagination;
  Long totalQuotes;
  List<Quote> data;

  public QuoteDto() {
  }

  public QuoteDto(String message, PaginationDto pagination, Long totalQuotes,
      List<Quote> data) {
    this.message = message;
    this.pagination = pagination;
    this.totalQuotes = totalQuotes;
    this.data = data;
  }
}
