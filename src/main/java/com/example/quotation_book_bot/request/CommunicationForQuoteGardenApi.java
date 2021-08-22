package com.example.quotation_book_bot.request;

import com.example.quotation_book_bot.dto.QuoteDto;
import com.example.quotation_book_bot.entity.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class CommunicationForQuoteGardenApi {

  @Autowired
  private RestTemplate restTemplate;

  private final String URL = "https://quote-garden.herokuapp.com/api/v3/quotes/random";

  public Quote getRandomQuote() {

    ResponseEntity<QuoteDto> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
        new ParameterizedTypeReference<QuoteDto>() {
        });
    QuoteDto quoteDto = responseEntity.getBody();
    Quote randomQuote = quoteDto.getData().get(0);
    log.info(responseEntity.toString());
    log.info(randomQuote.toString());

    return randomQuote;
  }


}
