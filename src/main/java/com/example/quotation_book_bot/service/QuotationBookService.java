package com.example.quotation_book_bot.service;

import com.example.quotation_book_bot.entity.BotUser;
import com.example.quotation_book_bot.entity.Quote;
import com.example.quotation_book_bot.repository.QuoteRepository;
import com.example.quotation_book_bot.repository.UserRepository;
import com.example.quotation_book_bot.request.CommunicationForQuoteGardenApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class QuotationBookService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CommunicationForQuoteGardenApi communication;
  @Autowired
  private QuoteRepository quoteRepository;

  public BotUser findUserByTgId(Long tgId) {
    return userRepository.findById(tgId).orElse(null);
  }

  public void saveOrUpdateBotUser(BotUser botUser) {

    userRepository.saveAndFlush(botUser);
  }

  public Quote getRandomQuote() {
    return communication.getRandomQuote();
  }

  public Quote getQuoteFromText(String text) {
    return quoteRepository.findQuoteByQuoteText(text);
  }
}
