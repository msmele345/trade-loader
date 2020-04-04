package com.mitchmele.tradeloader.mongodb;

import com.mitchmele.tradeloader.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends MongoRepository<Stock, String> {
}
