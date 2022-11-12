package com.doherty.dbwriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

@Service
public class MessageService {

    @Autowired
    private Environment environment;

    @Bean
    public Consumer<Stock> stockReceived() {
        return stock -> {
            System.out.println("Received: " + stock.toString());
            if (stock.getTicker().equals("FAKETICKER")) {
                return;
            }
            try {
                String url = environment.getProperty("spring.datasource.url");
                String username = environment.getProperty("spring.datasource.username");
                String password = environment.getProperty("spring.datasource.password");
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO stocks(country, currency, exchange, ipo, marketCapitalization, name, phone, shareOutstanding, ticker, webUrl, logo, finnhubIndustry) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
                statement.setString(1, stock.getCountry());
                statement.setString(2, stock.getCurrency());
                statement.setString(3, stock.getExchange());
                statement.setString(4, stock.getIpo());
                statement.setInt(5, stock.getMarketCapitalization());
                statement.setString(6, stock.getName());
                statement.setString(7, stock.getPhone());
                statement.setDouble(8, stock.getShareOutstanding());
                statement.setString(9, stock.getTicker());
                statement.setString(10, stock.getWeburl());
                statement.setString(11, stock.getLogo());
                statement.setString(12, stock.getFinnhubIndustry());
                statement.executeUpdate();
                connection.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        };
    }

}
