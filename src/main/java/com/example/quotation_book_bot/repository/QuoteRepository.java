package com.example.quotation_book_bot.repository;

import com.example.quotation_book_bot.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, String> {

  Quote findQuoteByQuoteText(String text);

}
