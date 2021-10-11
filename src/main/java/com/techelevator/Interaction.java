package com.techelevator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Interaction {
    private int menuSelection;
    private final Scanner input;
    private FileWriter fileWriter;
    private final VendingMachineCLI vendingMachine;
    private final Display display;

    public Interaction(Display display, VendingMachineCLI vendingMachine) {
        input = new Scanner(System.in);

        try {
            fileWriter = new FileWriter("log.txt");
        }
        catch(IOException exception) {
            display.logFileNotFound();
        }

        this.display = display;
        this.vendingMachine = vendingMachine;
    }

    public String input() {
        return input.nextLine();
    }

    public int getMenuSelection() {
        return menuSelection;
    }

    public void setMenuSelection(int maxNumber) {
        while(true) {
            try {
                menuSelection = Integer.parseInt(input.nextLine());
            }
            catch(NumberFormatException exception) {
                menuSelection = 0;
            }

            if(menuSelection >= 1 && menuSelection <= maxNumber) {
                break;
            }

            display.invalidInput();
        }
    }

    public void log(String date, String transaction, double beginningBalance, double endingBalance, boolean isSelection) {
        try {
            fileWriter.write(date + " "
                           + (isSelection ? vendingMachine.getSelectionMap().get(transaction).getName() + " " + transaction : transaction) + " "
                           + "\\" + Display.moneyFormat(beginningBalance) + " \\" + Display.moneyFormat(endingBalance) + "\n");

            fileWriter.flush();
        }
        catch(IOException exception) {
            display.failedToWriteToLogFile();
        }
    }

    public void log(String date, String transaction, double beginningBalance, double endingBalance) {
        log(date, transaction, beginningBalance, endingBalance, false);
    }
}
