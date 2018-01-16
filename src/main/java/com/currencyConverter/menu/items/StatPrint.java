package com.currencyConverter.menu.items;

import com.currencyConverter.core.Statistics;
import com.currencyConverter.menu.InputOutput;
import com.currencyConverter.menu.Item;

public class StatPrint extends Item {

    public StatPrint(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Print statistics graphics";
    }

    @Override
    public void perform() {
        Statistics.getStat();
    }
}