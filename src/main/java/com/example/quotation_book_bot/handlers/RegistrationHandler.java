package com.example.quotation_book_bot.handlers;

import com.example.quotation_book_bot.botUtils.BotState;
import com.example.quotation_book_bot.cache.StatementCache;
import com.example.quotation_book_bot.entity.BotUser;
import com.example.quotation_book_bot.service.QuotationBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Регистрация пользователя, запрашивает имя и добавляет BotUser в БД.
 */
@Component
public class RegistrationHandler implements MessageHandler {

  @Autowired
  private StatementCache statementCache;
  @Autowired
  private MainMenu mainMenu;
  @Autowired
  private QuotationBookService quotationBookService;


  private final BotState name = BotState.REGISTERING;

  /**
   * Получает информацию из Update, получает имя из сообщение и сохраняет пользователя в бд
   * @param update
   * @return BotApiMethod
   */
  @Override
  public BotApiMethod<?> handle(Update update) {
    Message message = update.getMessage();
    BotUser botUser = new BotUser();
    botUser.setAdmin(false);
    botUser.setName(message.getText());
    botUser.setTelegramId(message.getFrom().getId());
    quotationBookService.saveOrUpdateBotUser(botUser);

    statementCache.setState(message.getChatId(), BotState.MAIN_MENU);

    System.out.println(message.getFrom());
    return mainMenu.handle(update);
  }

  @Override
  public BotState getHandlerName() {
    return name;
  }
}
