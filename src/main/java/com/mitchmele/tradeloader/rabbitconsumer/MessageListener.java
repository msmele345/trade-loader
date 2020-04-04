package com.mitchmele.tradeloader.rabbitconsumer;

import com.mitchmele.tradeloader.StockTransformer;
import com.mitchmele.tradeloader.model.Stock;
import com.mitchmele.tradeloader.mongodb.MongoStockClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    MongoStockClient mongoStockClient;
    StockTransformer stockTransformer;

    public MessageListener(MongoStockClient mongoStockClient, StockTransformer stockTransformer) {
        this.mongoStockClient = mongoStockClient;
        this.stockTransformer = stockTransformer;
    }

    //String msg = new String(message);
    //handleMessage(byte[] stockJson)
//    @RabbitListener(queues = "${trade-loader.rabbitmq.queue}")
    public void handleMessage(String incomingJson) throws Exception {
        logger.info("Received Message: {} from Stocks Queue.", incomingJson);
        try {
            Stock newStock = stockTransformer.transform(incomingJson);
            mongoStockClient.insertStock(newStock);
        } catch (Exception e) {
            throw new Exception("An Error occurred: " + e.getLocalizedMessage());
        }
    }
}
