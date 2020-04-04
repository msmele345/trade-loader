package com.mitchmele.tradeloader.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "stocks")
public class Stock {

    String symbol;
    String bid;
    String offer;
    String lastPrice;

    final static String DEFAULT_SYMBOL = "XYZ";

    public Stock() {
        this.symbol = DEFAULT_SYMBOL;
        this.bid = "23.00";
        this.offer = "23.50";
        this.lastPrice = "23.40";
    }

    public Stock(String symbol, String bid, String offer, String lastPrice) {
        this.symbol = symbol;
        this.bid = bid;
        this.offer = offer;
        this.lastPrice = lastPrice;
    }
}
