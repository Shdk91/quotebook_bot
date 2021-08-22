package com.example.quotation_book_bot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotUser {
  String name;
  boolean isAdmin;
  long telegramId;

  public BotUser() {
  }

  public BotUser(String name, boolean isAdmin, long telegramId) {
    this.name = name;
    this.isAdmin = isAdmin;
    this.telegramId = telegramId;
  }
}
