package com.currencyConverter.core;


import com.currencyConverter.pojo.FixerCurrencyResponce;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CurrencyConvert {

    /* static final List<String> currNames = Arrays.asList(new String[]{"AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK",
             "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK",
             "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "ZAR", "EUR"});*/
    public static Set<String> currNames = getCurrencies();

    public static SimpleDateFormat dateOutput = new SimpleDateFormat("EEE, d MMM yyyy");


    public static FixerCurrencyResponce currs() throws IOException {
        SimpleDateFormat dateOutput = new SimpleDateFormat("EEE, d MMM yyyy");
        RestTemplate restTemplate = new RestTemplate();
        String base = "";
        Date date = new Date();
        String dateString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currs = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        Properties properties = new Properties();
        InputStream is = CurrencyConvert.class.getResourceAsStream("/application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {

            String err = "Sorry file of properties can't be readed, log saved to log file.";
            String str = dateOutput.format(date) + "\n" + err + "\n" + e.toString();
            printLogsToFile(str);
            System.err.println(err);
            return null;
        }

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
            try {
                currs = br.readLine();
            } catch (IOException e) {
                String err = "Sorry something gone wrong, log saved to log file.";
                String string = dateOutput.format(new Date()) + "\n" + err + "\n" + e.toString();
                printLogsToFile(string);
                System.err.println(err);
                return null;
            }
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
        /*try {
            br.close();
        } catch (IOException e) {
            String err = "Sorry something gone wrong, log saved to log file.";
            String string = dateOutput.format(new Date()) + "\n" + err + "\n" + e.toString();
            printLogsToFile(string);
            System.err.println(err);
            return null;
        }*/


        Map<String, String> requestSetters = new HashMap<>();
        requestSetters.put(properties.getProperty("date"), dateFormat.format(date));
        requestSetters.put(properties.getProperty("currs"), currs);
        requestSetters.put(properties.getProperty("base"), base);

        String url = properties.getProperty("url");

        FixerCurrencyResponce body = restTemplate.getForEntity(url, FixerCurrencyResponce.class, requestSetters).getBody();
        return body;
    }

    public static void printConvertation() throws IOException {
        System.out.println(currs());
    }

    public static void printLogsToFile(String str){
        Properties properties = new Properties();
        InputStream is = CurrencyConvert.class.getResourceAsStream("/application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            String err = "Sorry file of properties can't be readed, log saved to log file.";
            String string = dateOutput.format(new Date()) + "\n" + err + "\n" + e.toString();
            printLogsToFile(string);
            System.err.println(err);
            return;
        }
        try {
            is.close();
        } catch (IOException e) {
            String err = "Sorry something gone wrong, log saved to log file.";
            String string = dateOutput.format(new Date()) + "\n" + err + "\n" + e.toString();
            printLogsToFile(string);
            System.err.println(err);
            return;
        }
        File logFile = new File(properties.getProperty("logFile"));
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(logFile, true));
        } catch (IOException e) {

        }
        try {

            outputStream.writeChars("\n--------\n" + str);
        } catch (IOException e) {
            String err = "Sorry log file can't be readed, log saved to log file.";
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            String err = "Sorry something gone wrong, log saved to log file.";
            String string = dateOutput.format(new Date()) + "\n" + err + "\n" + e.toString();
            printLogsToFile(string);
            System.err.println(err);
            return;
        }

    }

    public static HashSet<String> getCurrencies() {
        Properties properties = new Properties();
        InputStream is = CurrencyConvert.class.getResourceAsStream("/application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            String err = "Sorry file of properties can't be readed, log saved to log file.";
            String string = dateOutput.format(new Date()) + "\n" + err + "\n" + e.toString();
            printLogsToFile(string);
            System.err.println(err);
        }
        RestTemplate restTemplate = new RestTemplate();
        FixerCurrencyResponce body = restTemplate.getForEntity(properties.getProperty("urlCurrs"), FixerCurrencyResponce.class).getBody();
        Set<String> pocket = body.getRates().keySet();
        HashSet<String> currencies = new HashSet<>(pocket);
        currencies.add("USD");
        return currencies;

    }
}

