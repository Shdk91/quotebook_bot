package com.example.quotation_book_bot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quote {
  String _id;
  String quoteText;
  String quoteAuthor;
  String quoteGenre;
  Long __v;

  public Quote() {
  }

  public Quote(String _id, String quoteText, String quoteAuthor, String quoteGenre,
      Long __v) {
    this._id = _id;
    this.quoteText = quoteText;
    this.quoteAuthor = quoteAuthor;
    this.quoteGenre = quoteGenre;
    this.__v = __v;
  }

  @Override
  public String toString() {
    return "Quote{" +
        "_id='" + _id + '\'' +
        ", quoteText='" + quoteText + '\'' +
        ", quoteAuthor='" + quoteAuthor + '\'' +
        ", quoteGenre='" + quoteGenre + '\'' +
        ", __v=" + __v +
        '}';
  }
}
