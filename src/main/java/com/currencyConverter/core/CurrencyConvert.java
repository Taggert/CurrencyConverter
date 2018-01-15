package com.currencyConverter.core;


import com.currencyConverter.pojo.FixerCurrencyResponce;
import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CurrencyConvert {

    static final List<String> currNames = Arrays.asList(new String[]{"AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK",
            "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK",
            "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "ZAR", "EUR"});

    @SneakyThrows
    public static void currs() {
        RestTemplate restTemplate = new RestTemplate();

        String base = "";
        Date date = new Date();
        String dateString;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currs = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        while (flag) {
            System.out.println("Input date in format YYYY-MM-DD, or press enter to use today's date:");
            dateString = br.readLine();
            if (!dateString.equals("")) {
                try {
                    date = dateFormat.parse(dateString);
                    flag = false;
                } catch (ParseException e) {
                    System.err.println("Wrong date type!");
                    flag = true;
                }
            } else {
                flag = false;
            }
        }
        flag = true;
        while (flag) {
            System.out.println("Input base currency(USD, EUR, RUB, ILS, etc., to see all variants type CUR):");
            base = br.readLine();
            base = base.replace(" ", "");
            if (!currNames.contains(base)) {
                if (base.equalsIgnoreCase("cur")) {
                    for (String str : currNames) {
                        System.out.println(str);
                    }
                } else {
                    System.err.println("You have to input right currency!");
                }
            } else {
                flag = false;
            }
        }
        flag = true;
        input:
        while (flag) {
            System.out.println("Input currencies to be rated(in format CURR1,CURR2,CURR3...etc., to see all variants type CUR):");
            currs = br.readLine();
            currs = currs.replace(" ", "");
            if (currs.equalsIgnoreCase("cur")) {
                for (String str : currNames) {
                    System.out.println(str);
                }
            } else {
                List<String> rates = Arrays.asList(currs.split(","));
                for (String cur : rates) {
                    if (!currNames.contains(cur)) {
                        System.err.println("You have to input right currency! " + cur + " is wrong.");
                        continue input;
                    }
                }
                StringBuilder ratesString = new StringBuilder();
                for (String rate : rates) {
                    ratesString.append(rate + ",");
                }
                currs = ratesString.toString().substring(0, (ratesString.length() - 1));
                flag = false;
            }

        }
        br.close();


        Map<String, String> requestSetters = new HashMap<>();
        requestSetters.put("latest", dateFormat.format(date));
        requestSetters.put("currs", currs);
        requestSetters.put("base", base);

        String url = "https://api.fixer.io/{latest}?symbols={currs}&base={base}";

        FixerCurrencyResponce body = restTemplate.getForEntity(url, FixerCurrencyResponce.class, requestSetters).getBody();
        System.out.println(body.toString());
    }
}

