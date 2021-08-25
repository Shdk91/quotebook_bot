package com.example.quotation_book_bot.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class BotUser {

  @Id
  @Column(name = "id")
  long telegramId;

  @Column(name = "name")
  String name;
  @Column(name = "isadmin")
  boolean isAdmin;


  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE
      , CascadeType.REFRESH, CascadeType.DETACH})
  @JoinTable(name = "user_quote"
      , joinColumns = @JoinColumn(name = "user_id")
      , inverseJoinColumns = @JoinColumn(name = "quote_id"))
  List<Quote> quotes;

  public BotUser() {
  }

  public BotUser(String name, boolean isAdmin, long telegramId) {
    this.name = name;
    this.isAdmin = isAdmin;
    this.telegramId = telegramId;
  }

  public void addQuote(Quote quote) {
    if (quotes == null) {
      quotes = new ArrayList<>();
    }
    quotes.add(quote);
  }

  public void deleteQuote(Quote quote) {
    quotes.remove(quote);
  }

  @Override
  public String toString() {
    return "BotUser{" +
        "name='" + name + '\'' +
        ", isAdmin=" + isAdmin +
        ", telegramId=" + telegramId +
        '}';
  }
}
