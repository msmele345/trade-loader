package com.mitchmele.tradeloader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mitchmele.tradeloader.model.Stock;
import lombok.NoArgsConstructor;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class StockTransformer implements GenericTransformer<String, Stock> {

    @Override
    public Stock transform(String source) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(source, Stock.class);
        } catch (JsonSyntaxException e) {
            e.getLocalizedMessage();
            throw new JsonSyntaxException(e.getLocalizedMessage());
        }
    }
}
