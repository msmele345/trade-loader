package com.mitchmele.tradeloader;

import com.google.gson.JsonSyntaxException;
import com.mitchmele.tradeloader.model.Stock;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StockTransformerTest {

    StockTransformer subject;

    @Before
    public void setUp() throws Exception {
        subject = new StockTransformer();
    }

    @Test
    public void transform_success_shouldTransformIncomingStringToStock() {

        String incomingJson = "{\"symbol\": \"XYZ\", \"bid\": \"10.50\", \"offer\":  \"10.75\", \"lastPrice\": \"10.60\"}";
        Stock expected = new Stock("XYZ", "10.50", "10.75", "10.60");

        Stock actual = subject.transform(incomingJson);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void transform_failure_shouldThrowJsonException() {
        String badJson = "{\"symbol\": \"XYZ\", \"bid\": \"10.50\", \"offer\"}";

        assertThatThrownBy(() -> subject.transform(badJson))
                .isInstanceOf(JsonSyntaxException.class)
                .hasMessage("com.google.gson.stream.MalformedJsonException: Expected ':' at line 1 column 43 path $.offer");
    }
}