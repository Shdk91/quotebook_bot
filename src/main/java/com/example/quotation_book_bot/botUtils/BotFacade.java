package com.example.quotation_book_bot.botUtils;

import com.example.quotation_book_bot.cache.QuoteCache;
import com.example.quotation_book_bot.cache.StatementCache;
import com.example.quotation_book_bot.entity.Quote;
import com.example.quotation_book_bot.handlers.MessageHandler;
import com.example.quotation_book_bot.service.QuotationBookService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Фасад для обработчиков сообщений, распределяет сообщения по необходимым обработчикам.
 */

@Component
@Slf4j
public class BotFacade {

  @Autowired
  private StatementCache statementCache;
  @Autowired
  private QuoteCache quoteCache;
  @Autowired
  private QuotationBookService quotationBookService;

  private Map<BotState, MessageHandler> handlers = new HashMap<>();

  public BotFacade(List<MessageHandler> messageHandlers) {
    messageHandlers.forEach(messageHandler ->
        this.handlers.put(messageHandler.getHandlerName(), messageHandler));
  }

  /**
   * Получает update из контроллера, получает из кэша состояние бота и передает update в нужный обработчик
   * @param update - Update получаемый из TelegramBotApi
   * @return BotApiMethod
   */
  public BotApiMethod<?> handleUpdate(Update update) {

    if (update.hasCallbackQuery()){
      CallbackQuery callbackQuery = update.getCallbackQuery();
      return processCallbackQuery(update);
    } else {
      Message message = update.getMessage();

      log.info("New Message from userId: {}, chatID: {}, with text: {}", message.getFrom().getId(),
          message.getChatId(), message.getText());

      Long id = message.getChatId();
      BotState botState = statementCache.getState(id);
      MessageHandler handler = handlers.get(botState);

      if (handler == null) {
        handler = handlers.get(BotState.START);
        statementCache.setState(id, BotState.START);
      } else if (!handler.getHandlerName().equals(BotState.REGISTERING)){
        handler = handlers.get(BotState.MAIN_MENU);
      }

      return handler.handle(update);
    }
  }

  private BotApiMethod<?> processCallbackQuery(Update update) {
    CallbackQuery callbackQuery = update.getCallbackQuery();
    Long chatId = callbackQuery.getMessage().getChatId();
    Long userId = callbackQuery.getFrom().getId();
    String data = callbackQuery.getData();

    BotApiMethod<?> response;

    log.info("New callbackQuery from userId: {}, chatID: {}, with data: {}", userId, chatId, data);

    //Обработка конопок из Main menu
    if (data.equals("Get random quote")){
      response = handlers.get(BotState.RANDOM_QUOTE).handle(update);
      statementCache.setState(chatId, BotState.RANDOM_QUOTE);
      return response;
    } else if (data.equals("Get quotes list")) {
      response = handlers.get(BotState.GET_QUOTE_LIST).handle(update);
      statementCache.setState(chatId, BotState.GET_QUOTE_LIST);
      return response;
    }

    //обработка кнопок из RandomQuoteHandler кроме Get next random quote
    if (data.equals("Add in list")){
      String text = update.getCallbackQuery().getMessage().getText().toString().split("\n")[0];
      Quote quote = quoteCache.getQuote(text);
      log.info(quote.toString());
      quotationBookService.saveQuote(userId, quote);
      AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
      answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
      answerCallbackQuery.setShowAlert(true);
      answerCallbackQuery.setText("цитата сохранена");
      return answerCallbackQuery;
    }

    return null;
  }
}
