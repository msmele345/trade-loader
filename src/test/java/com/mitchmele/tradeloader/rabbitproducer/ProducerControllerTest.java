package com.mitchmele.tradeloader.rabbitproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchmele.tradeloader.model.Stock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProducerControllerTest {

    ProducerController subject;
    RabbitSenderService mockSenderService = mock(RabbitSenderService.class);

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        subject = new ProducerController(mockSenderService);
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
    }

    @Test
    public void produceStock_success_shouldCallRabbitService() throws Exception {


        Stock stock1 = new Stock();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/rabbitmq/producer")
                .content(asJsonString(stock1))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.symbol").exists())
                .andExpect(status().isOk());

        verify(mockSenderService).send(any());
    }


    @Test
    public void produceStock_failure_shouldReturnResponseEntityWithError_IfSendFails() throws Exception {

        when(mockSenderService.send(any(String.class))).thenThrow(new Exception("something bad happened"));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/rabbitmq/producer")
                .content("bad message")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    public static String asJsonString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}