package com.example.quotation_book_bot.controller;

import com.example.quotation_book_bot.QuotationBookBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {
  @Autowired
  private final QuotationBookBot quotationBookBot;

  public WebHookController(QuotationBookBot quotationBookBot) {
    this.quotationBookBot = quotationBookBot;
  }

  @PostMapping
  public BotApiMethod<?> onUpdateReceived(@RequestBody Update update){
    System.out.println(update.getMessage().getText());
    return quotationBookBot.onWebhookUpdateReceived(update);
  }
}
