package com.example.quotation_book_bot.controller;

import com.example.quotation_book_bot.botUtils.BotFacade;
import com.example.quotation_book_bot.service.QuotationBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

  @Autowired
  private BotFacade botFacade;

  @PostMapping
  public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
    return botFacade.handleUpdate(update);
  }
}
