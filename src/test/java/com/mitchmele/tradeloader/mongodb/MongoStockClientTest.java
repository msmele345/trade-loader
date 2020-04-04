package com.mitchmele.tradeloader.mongodb;

import com.mitchmele.tradeloader.model.Stock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

public class MongoStockClientTest {

    MongoStockClient subject;

    TradeRepository mockRepo = mock(TradeRepository.class);

    @Before
    public void setUp() throws Exception {
        subject = new MongoStockClient(mockRepo);
    }

    @Test
    public void getAllStocks_success_shouldCallTheRepositoryFindAll() throws IOException {
        subject.getAllStocks();
        verify(mockRepo).findAll();
    }

    @Test
    public void getAllStocks_success_shouldReturnListOfAllStocks_fromRepo() throws IOException {
        Stock stock1 = new Stock("XYZ", "2", "3", "2.50");
        Stock stock2 = new Stock("AAPL", "200.25", "202.50", "201.00");
        Stock stock3 = new Stock("MSFT", "114.67", "114.90", "114.80");

        List<Stock> expectedList = Arrays.asList(stock1, stock2, stock3);

        when(mockRepo.findAll()).thenReturn(expectedList);

        List<Stock> actual = subject.getAllStocks();

        assertThat(actual).isEqualTo(expectedList);
    }

    @Test
    public void getAllStocks_failure_shouldIOExceptionIfMongoCallFails() {

        when(mockRepo.findAll()).thenThrow(new RuntimeException("something bad happened"));

        assertThatThrownBy(() -> subject.getAllStocks())
                .isInstanceOf(IOException.class)
                .hasMessage("something bad happened");
    }

    @Test
    public void insertStock_success_shouldCallTheRepositoryMethodInsert() throws IOException {
        Stock newStock = new Stock("MSFT", "114.67", "114.90", "114.80");
        subject.insertStock(newStock);

        ArgumentCaptor<Stock> captor = ArgumentCaptor.forClass(Stock.class);

        verify(mockRepo).insert(captor.capture());
        assertThat(captor.getValue()).isEqualTo(newStock);
    }

    @Test
    public void insertStock_failure_shouldThrowIOExceptionIfInsertFails() {

        when(mockRepo.insert(any(Stock.class))).thenThrow(new RuntimeException("bad insert"));

        assertThatExceptionOfType(IOException.class)
                .isThrownBy(() -> subject.insertStock(new Stock()))
                .withMessage("Stock: Stock(symbol=XYZ, bid=23.00, offer=23.50, lastPrice=23.40) " +
                        "has an exception on insert with message bad insert");
    }
}