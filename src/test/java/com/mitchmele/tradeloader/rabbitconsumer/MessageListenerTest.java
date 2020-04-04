package com.mitchmele.tradeloader.rabbitconsumer;

import com.google.gson.JsonSyntaxException;
import com.mitchmele.tradeloader.StockTransformer;
import com.mitchmele.tradeloader.mongodb.MongoStockClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class MessageListenerTest {

    StockTransformer mockTransformer = mock(StockTransformer.class);
    MongoStockClient mockMongoClient = mock(MongoStockClient.class);

    MessageListener subject;

    @Before
    public void setUp() throws Exception {
        subject = new MessageListener(mockMongoClient,mockTransformer);
    }

    @Test
    public void handleMessage_success_shouldProcessIncomingMessage() throws Exception {
        String incomingJson = "{\"symbol\":  \"xyz\", \"bid\": \"10.50\", \"offer\":  \"10.75\", \"lastPrice\":  \"10.60\"}";

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        subject.handleMessage(incomingJson);
        verify(mockTransformer).transform(captor.capture());
        verify(mockMongoClient).insertStock(any());
    }


    @Test
    public void handleMessage_failure_shouldThrowExceptionIfTransformerFails() throws Exception {

        when(mockTransformer.transform(any())).thenThrow(new JsonSyntaxException("bad message"));

        assertThatThrownBy(() -> subject.handleMessage("bad message"))
                .isInstanceOf(Exception.class)
                .hasMessage("An Error occurred: bad message");
    }
}