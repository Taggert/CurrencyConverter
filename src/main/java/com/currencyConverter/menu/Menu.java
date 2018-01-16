package com.currencyConverter.menu;
 
import java.util.ArrayList;

public class Menu {
 
    ArrayList<Item> items;
    InputOutput inputOutput;
 
    public Menu(ArrayList<Item> items, InputOutput inputOutput) {
        this.items = items;
        this.inputOutput = inputOutput;
    }
 
    public void runMenu() {
        int size = items.size();
        String errMsg = "";
        while (true) {
            if (errMsg != "") {
                inputOutput.put(errMsg);
            }
            for (int i = 1; i <= size; i++) {
                inputOutput.put(i + ". " + items.get(i - 1).displayedName());
            }
            int itemNumber = 0;
 
            try {
                errMsg = "";
                itemNumber = inputOutput.getInteger("\nEnter item number...");
                items.get(itemNumber - 1).perform();
                if (items.get(itemNumber - 1).isExit()) {
                    break;
                }
            } catch (Exception e) {
                errMsg = "\nItem number is wrong.\n";
            }
        }
 
    }
 
}