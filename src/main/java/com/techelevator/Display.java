package com.techelevator;

import java.util.Arrays;
import java.util.Map;

public class Display {
    VendingMachineCLI vendingMachine;

    public Display(VendingMachineCLI vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void displayItems() {
        String[] orderedSelectionMapKeys;
        Map<String, Item> selectionMap = vendingMachine.getSelectionMap();
        orderedSelectionMapKeys = selectionMap.keySet().toArray(new String[0]);
        Arrays.sort(orderedSelectionMapKeys);

        for(String key : orderedSelectionMapKeys) {
            System.out.println(key + " | " + selectionMap.get(key));
        }
    }

    public void dispense(Item item) {
        System.out.println("dispensing: " + item.getName() + "\n"
                         + moneyFormat("cost", item.getPrice()) + "\n"
                         + moneyFormat("money remaining", vendingMachine.getTotalMoneyProvided()));

        String sound;
        switch(item.getType()) {
            case "Chip":
                sound = "Crunch";
                break;
            case "Candy":
                sound = "Munch";
                break;
            case "Drink":
                sound = "Glug";
                break;
            default:
                sound = "Chew";
        }

        System.out.println(sound + " " + sound + ", Yum!");
    }

    public void dispensedChange(int quarters, int dimes, int nickels) {
        System.out.println("change: " + quarters + " quarters, " + dimes + " dimes, " + nickels + " nickels");
    }

    public void mainMenu() {
        menu(new String[]{"display vending machine items", "purchase", "exit"});
    }

    public void purchaseMenu() {
        menu(new String[]{"feed money", "select product", "finish transaction"});
        System.out.println(moneyFormat("money provided", vendingMachine.getTotalMoneyProvided()));
    }

    public void feedMoneyMenu() {
        menu(new String[]{"feed money", "exit"});
    }

    public void howMuchMoneyToFeed() {
        System.out.print("amount: ");
    }

    public void whatProduct() {
        System.out.print("slot: ");
    }

    public void stockFileNotFound() {
        fileNotFound("stock");
    }

    public void logFileNotFound() {
        fileNotFound("log");
    }

    public void failedToWriteToLogFile() {
        System.out.println("FAILED TO WRITE TO LOG FILE");
    }

    public void invalidInput() {
        System.out.println("INVALID INPUT");
    }

    public void insufficientFunds() {
        System.out.println("INSUFFICIENT FUNDS");
    }

    public void itemOutOfStock() {
        System.out.println("ITEM OUT OF STOCK");
    }

    private static void menu(String[] menuItems) {
        for(int i = 1; i <= menuItems.length; i++) {
            System.out.println("(" + i + ") " + menuItems[i - 1]);
        }
    }

    public static String moneyFormat(String description, double money) {
        return description + ": " + moneyFormat(money);
    }

    public static String moneyFormat(double money) {
        return String.format("$%.2f", money);
    }

    private void fileNotFound(String file) {
        System.out.println(file.toUpperCase() + " FILE NOT FOUND");
    }
}
