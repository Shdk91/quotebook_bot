package com.example.quotation_book_bot.handlers;

import com.example.quotation_book_bot.botUtils.BotFacade;
import com.example.quotation_book_bot.botUtils.BotState;
import com.example.quotation_book_bot.cache.StatementCache;
import com.example.quotation_book_bot.entity.BotUser;
import com.example.quotation_book_bot.service.QuotationBookService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Стартовый обработчик, если пользователя нет в БД предлагает зарегистрирвоаться,
 * если пользователь есть в БД переводит бота в состояние MainMenu и вызывает нужный обработчик.
 */
@Component
public class StartHandler implements MessageHandler {

  @Autowired
  private StatementCache statementCache;
  @Autowired
  private MainMenu mainMenu;
  @Autowired
  private QuotationBookService quotationBookService;

  private BotState name = BotState.START;

  @Override
  @SneakyThrows
  public BotApiMethod<?> handle(Update update) {
    Message message = update.getMessage();
    Long chatId = message.getChatId();
    Long tgId = message.getFrom().getId();
    BotUser botUser = quotationBookService.findUserByTgId(tgId);

    if (botUser == null) {
      String text = "Привет, мы не знакомы. Зарегистрируйся чтобы продолжить. "
          + "Для регистрации введи свое имя.";
      statementCache.setState(chatId, BotState.REGISTERING);
      return new SendMessage(chatId.toString(), text);

    } else {
      statementCache.setState(chatId, BotState.MAIN_MENU);
      return mainMenu.handle(update);
    }
  }

  @Override
  public BotState getHandlerName() {
    return name;
  }
}
