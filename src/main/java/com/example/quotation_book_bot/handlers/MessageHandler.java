package com.example.quotation_book_bot.handlers;

import com.example.quotation_book_bot.botUtils.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface MessageHandler {

  BotApiMethod<?> handle(Update update);

  BotState getHandlerName();
}
