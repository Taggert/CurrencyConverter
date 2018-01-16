package com.currencyConverter.core;

import com.currencyConverter.pojo.FixerCurrencyResponce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

public class CalcCurrencies {

    public static void calculate() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input amount of money");
        String str = "";
        boolean flag = true;
        int money = 0;
        while (flag) {
            try {
                str = br.readLine();
            } catch (IOException e) {
                String err = "Sorry something gone wrong, log saved to log file.";
                String string = CurrencyConvert.dateOutput.format(new Date()) + "\n" + err + "\n" + e.toString();
                CurrencyConvert.printLogsToFile(string);
                System.err.println(err);
                return;
            }
            if (!str.equals("")) {
                try {
                    money = Integer.parseInt(str);
                    flag = false;
                } catch (NumberFormatException e) {
                    flag = true;
                    System.err.println("Wrong input, try again");
                }
            } else {
                System.err.println("Wrong input, try again");
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            String err = "Sorry something gone wrong, log saved to log file.";
            String string = CurrencyConvert.dateOutput.format(new Date()) + "\n" + err + "\n" + e.toString();
            CurrencyConvert.printLogsToFile(string);
            System.err.println(err);
            return;
        }
        FixerCurrencyResponce body = CurrencyConvert.currs();
        System.out.println("In " + money + " " + body.getBase() + " - ");
        for (Map.Entry<String, Double> entry : body.getRates().entrySet()) {
            System.out.println((money * entry.getValue()) + " " + entry.getKey());
        }


    }


}