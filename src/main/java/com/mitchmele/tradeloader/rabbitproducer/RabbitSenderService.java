package com.mitchmele.tradeloader.rabbitproducer;

import com.mitchmele.tradeloader.mongodb.MongoStockClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Publisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RabbitSenderService {

    private final AmqpTemplate rabbitTemplate;

    @Value("${trade-loader.rabbitmq.exchange}")
    String exchange;

    @Value("${trade-loader.rabbitmq.queue}")
    String queueName;

    @Value("${trade-loader.rabbitmq.routingKey}")
    String routingKey;

    public RabbitSenderService(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String send(String message) throws Exception {
        //is this a stock now?
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Sending Message to Consumer: " + message);
        return "";
    }
}
