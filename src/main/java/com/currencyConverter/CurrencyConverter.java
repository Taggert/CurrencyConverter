package com.currencyConverter;

import com.currencyConverter.menu.*;
import com.currencyConverter.menu.items.CalculateCurrs;
import com.currencyConverter.menu.items.GetRates;
import com.currencyConverter.menu.items.StatPrint;

import java.io.IOException;
import java.util.ArrayList;

public class CurrencyConverter {

    static InputOutput inputOutput = new ConsoleInputOutput();
    public static void main(String[] args) throws IOException {

        //Starting menu
        ArrayList<Item> items = getItems();
        Menu menu = new Menu(items, inputOutput);
        menu.runMenu();
       /* BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("If you want to get currency rates on date, type 'r' \n" +
                "If you want to see statistics between two dates, type 's'\n" +
                "If you want to calculate currencies type 'c");
        String answer = null;
        try {
            answer = br.readLine();
        } catch (IOException e) {
            String err = "Sorry something gone wrong, log saved to log file.";
            String string = GettingRates.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
            GettingRates.printLogsToFile(string);
            System.err.println(err);
            return;
        }
        if(answer.equals("r")){
            GettingRates.printConvertation();
        }else if(answer.equals("s")){
            Statistics.getStat();
        }else if(answer.equals("c")){
            CalcCurrencies.calculate();
        }
        try {
            br.close();
        } catch (IOException e) {
            String err = "Sorry something gone wrong, log saved to log file.";
            String string = GettingRates.dateOutput.format(new Date())+"\n"+err+"\n"+e.toString();
            GettingRates.printLogsToFile(string);
            System.err.println(err);
            return;
        }*/
    }

    //Create menu items
    private static ArrayList<Item> getItems() {
        ArrayList<Item> res = new ArrayList<>();
        res.add(new GetRates(inputOutput));
        res.add(new CalculateCurrs(inputOutput));
        res.add(new StatPrint(inputOutput));
        res.add(new ExitItem(inputOutput));


        return res;
    }


}