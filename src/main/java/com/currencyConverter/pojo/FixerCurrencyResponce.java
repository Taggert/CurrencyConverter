package com.currencyConverter.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FixerCurrencyResponce {

    String base;
    Date date;
    Map<String, Double> rates;

    @Override
    public String toString() {
        return "Currency rates {" +
                "base = '" + base + '\'' +
                ", date = " + new SimpleDateFormat("EEE, d MMM yyyy").format(date) +
                ", rates = " + rates +
                '}';
    }
}