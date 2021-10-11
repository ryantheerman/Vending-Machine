package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Vending Machine Command Line Interface application
// This is Joseph Griffith's version
public class VendingMachineCLI {

	private final Map<String, Item> selectionMap;
	private double totalMoneyProvided;
	private Interaction interaction;
	private Display display;
	boolean isTest;

	public static void main(String[] args) {
		new VendingMachineCLI(false);
	}

	public VendingMachineCLI(boolean isTest) {
		this.isTest = isTest;
		selectionMap = new HashMap<>();
		if(stock()) {
			totalMoneyProvided = 0;
			display = new Display(this);
			interaction = new Interaction(display, this);
			run();
		}
	}

	private void run() {
		while(true) {
			display.mainMenu();
			interaction.setMenuSelection(3);

			if(interaction.getMenuSelection() == 3) {
				break;
			}

			if(interaction.getMenuSelection() == 1) {
				display.displayItems();
			}
			else {
				purchase();
			}
		}
	}

	private void purchase() {
		while(true) {
			display.purchaseMenu();
			interaction.setMenuSelection(3);

			if(interaction.getMenuSelection() == 3) {
				dispenseChange();
				break;
			}

			if(interaction.getMenuSelection() == 1) {
				feedMoney();
			}
			else {
				selectProduct();
			}
		}
	}

	private void feedMoney() {
		int amountOfMoney;
		while(true) {
			amountOfMoney = -1;
			display.feedMoneyMenu();
			interaction.setMenuSelection(2);

			if(interaction.getMenuSelection() == 2) {
				break;
			}

			while(true) {
				display.howMuchMoneyToFeed();

				try {
					amountOfMoney = Integer.parseInt(interaction.input());
				}
				catch(NumberFormatException ignored) {

				}

				if(amountOfMoney >= 0) {
					break;
				}

				display.invalidInput();
			}

			setTotalMoneyProvided(totalMoneyProvided + amountOfMoney);
			interaction.log(getDate(), "FEED MONEY:", totalMoneyProvided - amountOfMoney, totalMoneyProvided);
		}
	}

	private void selectProduct() {
		Item item;
		String slot;
		boolean sufficientFunds;

		display.displayItems();
		display.whatProduct();

		slot = interaction.input();

		if(!selectionMap.containsKey(slot)) {
			display.invalidInput();
			return;
		}

		item = selectionMap.get(slot);

		sufficientFunds = item.getPrice() <= getTotalMoneyProvided();

		if(!sufficientFunds) {
			display.insufficientFunds();
		}

		if(!item.isInStock()) {
			display.itemOutOfStock();
		}
		else if(sufficientFunds) {
			dispense(slot);
		}
	}

	private void dispense(String slot) {
		Item item = selectionMap.get(slot);
		totalMoneyProvided -= item.getPrice();
		item.decrementNumberOfItem();
		display.dispense(item);
		interaction.log(getDate(), slot, totalMoneyProvided + item.getPrice(), totalMoneyProvided, true);
	}

	private void dispenseChange() {
		double initialTotalMoneyProvided = getTotalMoneyProvided();
		int quarters = (int) (getTotalMoneyProvided() * 4);
		totalMoneyProvided = getTotalMoneyProvided() - 0.25 * quarters;
		int dimes = (int) (getTotalMoneyProvided() * 10);
		totalMoneyProvided = getTotalMoneyProvided() - 0.10 * dimes;
		int nickels = (int) (getTotalMoneyProvided() * 20);
		totalMoneyProvided = 0;
		display.dispensedChange(quarters, dimes, nickels);
		interaction.log(getDate(), "GIVE CHANGE:", initialTotalMoneyProvided, totalMoneyProvided);
	}

	private boolean stock() {
		Scanner fileScanner;
		String[] line;

		try {
			fileScanner = new Scanner(new File("vendingmachine.csv"));
		}
		catch(FileNotFoundException exception) {
			display.stockFileNotFound();
			return false;
		}

		while(fileScanner.hasNextLine()) {
			line = fileScanner.nextLine().split("\\|");

			selectionMap.put(line[0], new Item(line[1], Double.parseDouble(line[2]), line[3]));
		}

		return !isTest;
	}

	private String getDate() {
		return (new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")).format(new Date());
	}

	public Map<String, Item> getSelectionMap() {
		return selectionMap;
	}

	public double getTotalMoneyProvided() {
		return totalMoneyProvided;
	}

	public void setTotalMoneyProvided(double money) {
		totalMoneyProvided = money;
	}
}
