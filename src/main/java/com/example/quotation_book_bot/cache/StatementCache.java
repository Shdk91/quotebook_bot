package com.example.quotation_book_bot.cache;

import com.example.quotation_book_bot.botUtils.BotState;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class StatementCache implements BotCache{
  private Map<Long, BotState> stateCache = new ConcurrentHashMap<>();

  @Override
  public BotState getState(Long chatId) {
    return stateCache.get(chatId);
  }

  @Override
  public void setState(Long chatId, BotState botState) {
    stateCache.put(chatId, botState);
  }

  public Map<Long, BotState> getStateCache() {
    return stateCache;
  }
}
