package com.currencyConverter;

import com.currencyConverter.core.CurrencyConvert;
import com.currencyConverter.core.Statistics;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CurrConverter {

    @SneakyThrows
    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("If you want to get currency rates on date, type 'r' \n" +
                "If you want to see statistics between two dates, type 's'");
        String answer = br.readLine();
        if(answer.equals("r")){
            CurrencyConvert.currs();
        }else if(answer.equals("s")){
            Statistics.getStat();
        }
        br.close();
    }


}