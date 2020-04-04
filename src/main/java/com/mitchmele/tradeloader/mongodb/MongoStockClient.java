package com.mitchmele.tradeloader.mongodb;

import com.mitchmele.tradeloader.model.Stock;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class MongoStockClient {

    TradeRepository tradeRepository;

    public MongoStockClient(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Stock> getAllStocks() throws IOException {
        try {
            return tradeRepository.findAll();
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public void insertStock(Stock stock) throws IOException {
        try {
            tradeRepository.insert(stock);
        } catch (Exception e) {
            String msg = String.format("Stock: %s has an exception on insert with message %s", stock, e.getLocalizedMessage());
            throw new IOException(msg);
        }
    }
}
