package com.example.quotation_book_bot;

import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Setter
@Component
public class QuotationBookBot extends TelegramWebhookBot {

  @Value("${my.telegrambot.webHookPath}")
  private String webHookPath;
  @Value("${my.telegrambot.botUserName}")
  private String botUserName;
  @Value("${my.telegrambot.botToken}")
  private String botToken;


  @Override
  public String getBotUsername() {
    return botUserName;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  @SneakyThrows
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
   return null;
  }

  @Override
  public String getBotPath() {
    return webHookPath;
  }


}
