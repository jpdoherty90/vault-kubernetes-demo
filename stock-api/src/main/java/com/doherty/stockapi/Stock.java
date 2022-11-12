package com.doherty.stockapi;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class Stock implements Serializable {

    public String country;
    public String currency;
    public String exchange;
    public String ipo;
    public int marketCapitalization;
    public String name;
    public String phone;
    public double shareOutstanding;
    public String ticker;
    public String weburl;
    public String logo;
    public String finnhubIndustry;

}
