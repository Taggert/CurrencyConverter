package com.currencyConverter.menu.items;

import com.currencyConverter.core.GettingRates;
import com.currencyConverter.menu.InputOutput;
import com.currencyConverter.menu.Item;

import java.io.IOException;

public class GetRates extends Item {

    public GetRates(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Get rates";
    }

    @Override
    public void perform() {
        try {
            inputOutput.put(GettingRates.currs());
        } catch (IOException e) {

        }
    }
}