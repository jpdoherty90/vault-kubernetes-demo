package com.doherty.stockapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class StockController {

    @Autowired
    private Environment environment;

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/test")
    public String test() {
        return "It works";
    }

    @GetMapping("/stock/{symbol}")
    public Stock getStockData(@PathVariable String symbol) {

        System.out.println("Inside the getStockData method");

        // Get stock data from FinnHub
        String apiKey = environment.getProperty("finnhub.api-key");
        final String uri = "https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Making API call");
        Stock stock = restTemplate.getForObject(uri, Stock.class);
        System.out.println("Have data from API call:");
        System.out.println(stock.toString());

        // Drop data on queue
        System.out.println("SENDING TO TOPIC:\n" + stock.toString());

        sendToStreamBridge(stock);

        // Return data
        return stock;
    }

    public void sendToStreamBridge(Stock stock) {
        streamBridge.send("stocks", stock);
    }



}
