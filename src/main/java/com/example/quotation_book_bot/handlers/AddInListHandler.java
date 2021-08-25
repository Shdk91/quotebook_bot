package com.example.quotation_book_bot.handlers;

import com.example.quotation_book_bot.botUtils.BotState;
import com.example.quotation_book_bot.cache.QuoteCache;
import com.example.quotation_book_bot.entity.BotUser;
import com.example.quotation_book_bot.entity.Quote;
import com.example.quotation_book_bot.service.QuotationBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AddInListHandler implements MessageHandler {

  private final BotState name = BotState.ADD_IN_LIST;

  @Autowired
  private QuoteCache quoteCache;
  @Autowired
  private QuotationBookService quotationBookService;

  /**
   * Получает информацию из Update, находит нужного юзера добавляет цитату в список данного юзера,
   * после добавления цитаты удаляет ее из кэша,
   * обновляет в БД нужного пользователя.
   * @param update
   * @return BotApiMethod
   */
  @Override
  public BotApiMethod<?> handle(Update update) {
    CallbackQuery callbackQuery = update.getCallbackQuery();
    Long tgId = callbackQuery.getFrom().getId();
    BotUser botUser = quotationBookService.findUserByTgId(tgId);
    String text = callbackQuery.getMessage().getText().split("\n")[0];
    Quote quote = quoteCache.getQuote(text);

    botUser.addQuote(quote);
    quotationBookService.saveOrUpdateBotUser(botUser);
    quoteCache.deleteQuote(text);

    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
    answerCallbackQuery.setShowAlert(true);
    answerCallbackQuery.setText("Quote saved");

    return answerCallbackQuery;
  }

  @Override
  public BotState getHandlerName() {
    return name;
  }
}
