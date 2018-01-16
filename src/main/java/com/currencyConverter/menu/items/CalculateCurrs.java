package com.currencyConverter.menu.items;

import com.currencyConverter.core.CalcCurrencies;
import com.currencyConverter.menu.InputOutput;
import com.currencyConverter.menu.Item;

import java.io.IOException;

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
        try {
            CalcCurrencies.calculate();
        } catch (IOException e) {

        }
    }
}