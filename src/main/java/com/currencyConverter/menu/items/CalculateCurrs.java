package com.currencyConverter.menu.items;

import com.currencyConverter.core.CalcCurrencies;
import com.currencyConverter.menu.InputOutput;
import com.currencyConverter.menu.Item;

public class CalculateCurrs extends Item {

    public CalculateCurrs(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Calculate currensies";
    }

    @Override
    public void perform() {
        CalcCurrencies.calculate();
    }
}