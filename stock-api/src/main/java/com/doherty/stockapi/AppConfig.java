package com.doherty.stockapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Autowired
    private StockController stockController;

    @Bean
    CommandLineRunner initRabbit() {
        return args -> {
            try {
                System.out.println("SENDING FAKE STOCK MESSAGE");
                Stock stock = new Stock();
                stock.ticker = "FAKETICKER";
                stockController.sendToStreamBridge(stock);
                System.out.println("FAKE STOCK SENT");
            } catch (Exception e) {
                System.out.println("GOT AN EXCEPTION IN COMMAND LINE RUNNER");
            }

        };
    }

}
