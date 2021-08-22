package com.example.quotation_book_bot.repository;

import com.example.quotation_book_bot.entity.BotUser;
import com.example.quotation_book_bot.entity.Quote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.User;

@Repository
public class BotRepository {
  private Map<Long, BotUser> botUsers = new HashMap();
  private Map<Long, List<Quote>> quoteMap = new HashMap();

  public void addUser(User user, String name){
    BotUser botUser = new BotUser( name, false, user.getId());
    botUsers.put(user.getId(), botUser);
  }

  public BotUser findUserById(Long id) {
    return botUsers.get(id);
  }

  public void saveOrUpdateBotUser(BotUser botUser) {
    botUsers.put(botUser.getTelegramId(), botUser);
  }

  public void addQuote(Long userId, Quote quote) {
    List<Quote> quotes = quoteMap.get(userId);
    if (quotes == null) {
      quotes = new ArrayList<>();
      quotes.add(quote);
      quoteMap.put(userId, quotes);
    } else {
      quotes.add(quote);
    }
  }

  public List<Quote> getQuotes(Long userId) {
    return quoteMap.get(userId);
  }
}
