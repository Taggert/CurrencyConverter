package com.currencyConverter.core;

import com.currencyConverter.pojo.FixerCurrencyResponce;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
public class Statistics {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static Date dateStart = new Date();
    private static Date dateFinish = new Date();
    private static int step;
    private static String base;
    private static String curr;

    public static void getStat()  {
        setFields();
        drawStatistics();
    }

   //Fill in fields of json from keyboard
    private static void setFields()  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        String str;
        while (flag) {
            System.out.println("Input first date in format YYYY-MM-DD:");
            try {
                str = br.readLine();
            } catch (IOException e) {
                String err = "Sorry something gone wrong, log saved to log file.";
                String string = CurrencyConvert.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
                CurrencyConvert.printLogsToFile(string);
                System.err.println(err);
                return;
            }
            if (!str.equals("")) {
                try {
                    dateStart = dateFormat.parse(str);
                    flag = false;
                } catch (ParseException e) {
                    System.err.println("Wrong date type!");
                    flag = true;
                }
            } else {
                System.err.println("Date is empty!");
            }
        }
        flag = true;
        while (flag) {
            System.out.println("Input second date in format YYYY-MM-DD, or press enter to use today's date:\n" +
                    "Date should after the first date");
            try {
                str = br.readLine();
            } catch (IOException e) {
                String err = "Sorry something gone wrong, log saved to log file.";
                String string = CurrencyConvert.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
                CurrencyConvert.printLogsToFile(string);
                System.err.println(err);
                return;
            }
            if (!str.equals("")) {
                try {
                    dateFinish = dateFormat.parse(str);
                    flag = false;
                } catch (ParseException e) {
                    System.err.println("Wrong date type!");
                    flag = true;
                }
            } else {
                flag = false;
            }
            if (dateStart.equals(dateFinish) || dateStart.after(dateFinish)) {
                flag = true;
                System.err.println("Wrong date, try again");
            }
        }
        flag = true;
        while (flag) {
            System.out.println("Input statistics step in days:");
            try {
                str = br.readLine();
            } catch (IOException e) {
                String err = "Sorry something gone wrong, log saved to log file.";
                String string = CurrencyConvert.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
                CurrencyConvert.printLogsToFile(string);
                System.err.println(err);
                return;
            }
            if (!str.equals("")) {
                try {
                    step = Integer.parseInt(str);
                    flag = false;
                } catch (NumberFormatException e) {
                    flag = true;
                    System.err.println("Wrong input, try again");
                }
            } else {
                System.err.println("Wrong input, try again");
            }
        }
        flag = true;
        while (flag) {
            System.out.println("Input base currency(USD, EUR, RUB, ILS, etc., to see all variants type CUR):");
            try {
                base = br.readLine();
            } catch (IOException e) {
                String err = "Sorry something gone wrong, log saved to log file.";
                String string = CurrencyConvert.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
                CurrencyConvert.printLogsToFile(string);
                System.err.println(err);
                return;
            }
            base = base.replace(" ", "");
            if (!CurrencyConvert.currNames.contains(base)) {
                if (base.equalsIgnoreCase("cur")) {
                    for (String string : CurrencyConvert.currNames) {
                        System.out.println(string);
                    }
                } else {
                    System.err.println("You have to input right currency!");
                }
            } else {
                flag = false;
            }
        }
        flag = true;

        while (flag) {
            System.out.println("Input currency to be rated(USD, EUR, RUB, ILS, etc., to see all variants type CUR):");
            try {
                curr = br.readLine();
            } catch (IOException e) {
                String err = "Sorry something gone wrong, log saved to log file.";
                String string = CurrencyConvert.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
                CurrencyConvert.printLogsToFile(string);
                System.err.println(err);
                return;
            }
            curr = curr.replace(" ", "");
            if (curr.equalsIgnoreCase("cur")) {
                for (String string : CurrencyConvert.currNames) {
                    System.out.println(string);
                }
            } else if (curr.split(",").length != 1 || !CurrencyConvert.currNames.contains(curr)) {
                System.err.println("You have to input right currency! " + curr + " is wrong.");
            } else {
                flag = false;
            }
        }
        /*try {
            br.close();
        } catch (IOException e) {
            String err = "Sorry something gone wrong, log saved to log file.";
            String string = CurrencyConvert.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
            CurrencyConvert.printLogsToFile(string);
            System.err.println(err);
            return;
        }*/

    }

    //Send request to server to get rates of chosen currencies
    private static Map<Date, Double> getRates() {
        Properties properties = new Properties();
        InputStream is = Statistics.class.getResourceAsStream("/application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            String err = "Sorry file of properties can't be readed, log saved to log file.";
            String string = CurrencyConvert.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
            CurrencyConvert.printLogsToFile(string);
            System.err.println(err);
        }
        RestTemplate restTemplate = new RestTemplate();
        Map<Date, Double> res = new LinkedHashMap<>();
        Calendar calendar = Calendar.getInstance();
        for (calendar.setTime(dateStart); calendar.getTime().before(dateFinish) || calendar.getTime().equals(dateFinish); calendar.add(Calendar.DAY_OF_MONTH, step)) {
            Date date = calendar.getTime();
            Map<String, String> requestSetters = new HashMap<>();
            requestSetters.put(properties.getProperty("date"), dateFormat.format(date));
            requestSetters.put(properties.getProperty("currs"), curr);
            requestSetters.put(properties.getProperty("base"), base);
            String url = properties.getProperty("url");
            FixerCurrencyResponce body = restTemplate.getForEntity(url, FixerCurrencyResponce.class, requestSetters).getBody();
            res.put(date, body.getRates().get(curr));
        }
        return res;
    }

    //Print statistics of chosen currencies in inputed range of dates in graphics
    private static void drawStatistics(){
        Map<Date, Double> source = getRates();
        Map.Entry<Date, Double> min = source.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue)).get();
        System.out.println(base + " -> " + curr + " from " + new SimpleDateFormat("d MMM yyyy").format(dateStart)
                + " to " + new SimpleDateFormat("d MMM yyyy").format(dateFinish) + "\n----\n");
        for (Map.Entry<Date, Double> entry : source.entrySet()) {
            String star = "-";
            System.out.println(new SimpleDateFormat("d MMM yyyy").format(entry.getKey()) + "\t|  "
                    + starMultiply(star, ((entry.getValue() * 10) - (min.getValue() * 10) + 1)) + "> " + entry.getValue()+" "+curr);
        }
    }

    //Service method for drawing
    private static String starMultiply(String star, double index) {
        String res = "";
        for (int i = 0; i < index; i++) {
            res = res + star;
        }
        return res;
    }

}