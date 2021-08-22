package com.example.quotation_book_bot.handlers;

import com.example.quotation_book_bot.botUtils.BotState;
import com.example.quotation_book_bot.cache.QuoteCache;
import com.example.quotation_book_bot.entity.BotUser;
import com.example.quotation_book_bot.entity.Quote;
import com.example.quotation_book_bot.service.QuotationBookService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class RandomQuoteHandler implements MessageHandler{
  private final BotState name = BotState.RANDOM_QUOTE;

  @Autowired
  private QuotationBookService quotationBookService;
  @Autowired
  private QuoteCache quoteCache;

  @Override
  public BotApiMethod<?> handle(Update update) {
    CallbackQuery callbackQuery = update.getCallbackQuery();
    Long chatId = callbackQuery.getMessage().getChatId();
    Long userId = callbackQuery.getFrom().getId();
    Quote quote = quotationBookService.getRandomQuote();
    String text = quote.getQuoteText() + "\n\n" + quote.getQuoteAuthor();

    quoteCache.setQuote(quote.getQuoteText(), quote);

    SendMessage sendMessage = new SendMessage(chatId.toString(), text);
    sendMessage.setReplyMarkup(getInlineButtons());

    return sendMessage;
  }

  @Override
  public BotState getHandlerName() {
    return name;
  }

  private InlineKeyboardMarkup getInlineButtons() {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

    InlineKeyboardButton getNextRandomQuote = new InlineKeyboardButton("Get next random quote");
    InlineKeyboardButton addQuoteInList = new InlineKeyboardButton("Add in my list of quotes");

    getNextRandomQuote.setCallbackData("Get random quote");
    addQuoteInList.setCallbackData("Add in list");

    List<InlineKeyboardButton> buttonsRow1 = new ArrayList<>();
    buttonsRow1.add(getNextRandomQuote);

    List<InlineKeyboardButton> buttonsRow2 = new ArrayList<>();
    buttonsRow2.add(addQuoteInList);

    List<List<InlineKeyboardButton>> buttonsRows = new ArrayList<>();
    buttonsRows.add(buttonsRow1);
    buttonsRows.add(buttonsRow2);

    inlineKeyboardMarkup.setKeyboard(buttonsRows);

    return inlineKeyboardMarkup;
  }
}
