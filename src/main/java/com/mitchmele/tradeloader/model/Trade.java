package com.mitchmele.tradeloader.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "trades")
public class Trade {


    String tradePrice;
    String symbol;
    LocalDateTime tradeTime;

    public Trade(String tradePrice, String symbol, LocalDateTime tradeTime) {
        this.tradePrice = tradePrice;
        this.symbol = symbol;
        this.tradeTime = tradeTime;
    }
}
