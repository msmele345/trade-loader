package com.mitchmele.tradeloader.rabbitproducer;

import com.mitchmele.tradeloader.mongodb.MongoStockClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.core.AmqpTemplate;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RabbitSenderServiceTest {

    RabbitSenderService subject;

    AmqpTemplate mockTemplate = mock(AmqpTemplate.class);

    @Before
    public void setUp() throws Exception {
        subject = new RabbitSenderService(mockTemplate);
        subject.exchange = "test-exchange";
        subject.queueName = "test-queue";
        subject.routingKey = "test#";
    }

    @Test
    public void shouldCallTemplateWithArgs() throws Exception {
        subject.send("some message");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        List<String> captorValues = captor.getAllValues();

        verify(mockTemplate).convertAndSend(
               captor.capture(),
                captor.capture(),
                captor.capture()
        );
    }
}