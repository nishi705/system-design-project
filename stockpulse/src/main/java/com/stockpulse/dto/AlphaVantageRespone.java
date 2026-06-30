package com.stockpulse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AlphaVantageRespone(@JsonProperty("Global Quote") GlobalQuote globalQuote
) {

    // Make sure this says 'record', not 'class'
   public record GlobalQuote(
            @JsonProperty("01. symbol") String symbol,
            @JsonProperty("05. price") double price
    ) {
    }
}