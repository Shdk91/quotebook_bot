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
@Table(name = "quote")
public class Quote {

  @Id
  @Column(name = "id")
  String _id;

  @Column(name = "text")
  String quoteText;

  @Column(name = "author")
  String quoteAuthor;

  @Column(name = "genre")
  String quoteGenre;

  @Column(name = "v")
  Long __v;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE
      , CascadeType.REFRESH, CascadeType.DETACH})
  @JoinTable(name = "user_quote"
      , joinColumns = @JoinColumn(name = "quote_id")
      , inverseJoinColumns = @JoinColumn(name = "user_id"))
  List<BotUser> users;

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

  public void addBotUser(BotUser botUser) {
    if (users == null) {
      users = new ArrayList<>();
    }
    users.add(botUser);
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
