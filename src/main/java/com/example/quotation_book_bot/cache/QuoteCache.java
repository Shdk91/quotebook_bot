package com.example.quotation_book_bot.cache;

import com.example.quotation_book_bot.entity.Quote;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class QuoteCache {

  private final Map<String, Quote> quoteMap = new HashMap<>();

  public Quote getQuote(String text) {
    return quoteMap.get(text);
  }


  public void setQuote(String text, Quote quote) {
    quoteMap.put(text, quote);
  }

  public void deleteQuote(String text) {
    quoteMap.remove(text);
  }
}
