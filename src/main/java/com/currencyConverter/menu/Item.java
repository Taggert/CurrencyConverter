package com.currencyConverter.menu;

public abstract class Item {
	
	protected InputOutput inputOutput;

	public Item(InputOutput inputOutput) {
		this.inputOutput = inputOutput;
	}
	
	public abstract String displayedName();
	
	public abstract void perform();
	
	public boolean isExit(){
		return false;
	}

}
