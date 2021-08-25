package com.example.quotation_book_bot.handlers;

import com.example.quotation_book_bot.botUtils.BotState;
import com.example.quotation_book_bot.entity.BotUser;
import com.example.quotation_book_bot.service.QuotationBookService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * Основное меню бота, если бот находится в состоянии MainMenu, бот будет отдавать это меню на любое
 * сообщение.
 */
@Component
@Slf4j
public class MainMenu implements MessageHandler {

  private final BotState name = BotState.MAIN_MENU;

  @Autowired
  private QuotationBookService quotationBookService;

  @Override
  public BotState getHandlerName() {
    return name;
  }

  /**
   * Получает информацию из Update, находит нужного юзера, берет его имя
   * и выодит нужное сообщение с меню для пользователя.
   * @param update
   * @return BotApiMethod
   */
  @Override
  public BotApiMethod<?> handle(Update update) {

    Message message = update.getMessage();
    Long chatId = message.getChatId();
    Long userId = message.getFrom().getId();
    BotUser user = quotationBookService.findUserByTgId(userId);
    String text = "Hi, " + user.getName() + "! Choose an action:";

    SendMessage sendMessage = new SendMessage(chatId.toString(), text);
    sendMessage.setReplyMarkup(getInlineButtons());

    return sendMessage;
  }


  private InlineKeyboardMarkup getInlineButtons() {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

    InlineKeyboardButton getRandomQuote = new InlineKeyboardButton("Get random Quote");
    InlineKeyboardButton getQuoteList = new InlineKeyboardButton("Get my Quotes");

    getRandomQuote.setCallbackData("Get random quote");
    getQuoteList.setCallbackData("Get quotes list");

    List<InlineKeyboardButton> buttonsRow1 = new ArrayList<>();
    buttonsRow1.add(getRandomQuote);

    List<InlineKeyboardButton> buttonsRow2 = new ArrayList<>();
    buttonsRow2.add(getQuoteList);

    List<List<InlineKeyboardButton>> buttonsRows = new ArrayList<>();
    buttonsRows.add(buttonsRow1);
    buttonsRows.add(buttonsRow2);

    inlineKeyboardMarkup.setKeyboard(buttonsRows);

    return inlineKeyboardMarkup;
  }
}
