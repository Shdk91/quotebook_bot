package com.example.quotation_book_bot.handlers;

import com.example.quotation_book_bot.QuotationBookBot;
import com.example.quotation_book_bot.botUtils.BotState;
import com.example.quotation_book_bot.entity.Quote;
import com.example.quotation_book_bot.service.QuotationBookService;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class GetQuoteListHandler implements MessageHandler{
  final BotState name = BotState.GET_QUOTE_LIST;

  @Autowired
  private QuotationBookService quotationBookService;
  @Autowired
  private QuotationBookBot quotationBookBot;


  @Override
  public BotApiMethod<?> handle(Update update) {
    CallbackQuery callbackQuery = update.getCallbackQuery();
    Long chatId = callbackQuery.getMessage().getChatId();
    Long userId = callbackQuery.getFrom().getId();

    List<Quote> quoteList = quotationBookService.getQuotes(userId);
    quoteList.forEach(quote -> {
      try {
        quotationBookBot.execute(new SendMessage(chatId.toString(),
            quote.getQuoteText() + "\n\n" + quote.getQuoteAuthor()));
      } catch (TelegramApiException e) {
        log.info(e.getMessage());
      }
    });
    return null;
  }

  @Override
  public BotState getHandlerName() {
    return name;
  }
}
