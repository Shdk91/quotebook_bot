package com.example.quotation_book_bot.cache;

import com.example.quotation_book_bot.botUtils.BotState;

public interface BotCache {

  BotState getState(Long chatId);

  void setState(Long chatId, BotState botState);

}
