package com.example.quotation_book_bot.service;

import com.example.quotation_book_bot.entity.BotUser;
import com.example.quotation_book_bot.entity.Quote;
import com.example.quotation_book_bot.repository.BotRepository;
import com.example.quotation_book_bot.request.CommunicationForQuoteGardenApi;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuotationBookService {

  @Autowired
  private BotRepository botRepository;
  @Autowired
  private CommunicationForQuoteGardenApi communication;

  public BotUser findUserByTgId(Long tgId) {
    return botRepository.findUserById(tgId);
  }

  public void saveOrUpdateBotUser(BotUser botUser){

    botRepository.saveOrUpdateBotUser(botUser);
  }

  public Quote getRandomQuote() {
    return communication.getRandomQuote();
  }

  public void saveQuote(Long userId,Quote quote) {
    botRepository.addQuote(userId, quote);
  }

  public List<Quote> getQuotes(Long userId) {
    return botRepository.getQuotes(userId);
  }
}
