package com.example.quotation_book_bot.handlers;

import com.example.quotation_book_bot.botUtils.BotState;
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
public class DeleteQuoteHandler implements MessageHandler {

  private final BotState name = BotState.DELETE_QUOTE;

  @Autowired
  private QuotationBookService quotationBookService;

  @Override
  /**
   * Получает информацию из Update, находит нужного юзера удаляет цитату из списка данного юзера,
   * обновляет в БД нужного пользователя.
   * @param update
   * @return BotApiMethod
   */
  public BotApiMethod<?> handle(Update update) {
    CallbackQuery callbackQuery = update.getCallbackQuery();
    Long tgId = callbackQuery.getFrom().getId();
    BotUser botUser = quotationBookService.findUserByTgId(tgId);
    String text = callbackQuery.getMessage().getText().split("\n")[0];
    Quote quote = quotationBookService.getQuoteFromText(text);

    botUser.deleteQuote(quote);
    quotationBookService.saveOrUpdateBotUser(botUser);

    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
    answerCallbackQuery.setShowAlert(true);
    answerCallbackQuery.setText("Quote remove");

    return answerCallbackQuery;
  }

  @Override
  public BotState getHandlerName() {
    return name;
  }
}
