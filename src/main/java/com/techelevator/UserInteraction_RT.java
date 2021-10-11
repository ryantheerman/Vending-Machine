package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserInteraction_RT {

    private static Scanner kb;
    private VendingMachine_RT vm;
    private double moneyAdded = 0.0;
    private File logFile = new File("log_RT.txt");

    public File getLogFile() {
        return logFile;
    }

    public UserInteraction_RT(VendingMachine_RT vm) {
        this.vm = vm;
        this.kb = new Scanner(System.in);
    }

    public String getInput() {
        return kb.nextLine();
    }

    public double getMoneyAdded() {
        return moneyAdded;
    }

    public String getDate() {
        return (new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")).format(new Date());
    }

    public void mainMenu() {
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit");

        System.out.println("Select an option");

        boolean isValid = false;

        while (!isValid) {
            String input = getInput();
            if (input.equals("1")) {
                isValid = true;
                System.out.println();
                vm.displayItemMenu();
                System.out.println();
                mainMenu();
            } else if (input.equals("2")) {
                isValid = true;
                purchaseMenu();
            } else if (input.equals("3")) {
                isValid = true;
                System.out.println("Exiting...");
                System.exit(-1);
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    public void purchaseMenu() {
        System.out.println("(1) Feed Money");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction");

        System.out.println("Current Money provided: " + getMoneyAdded());

        System.out.println("Please select an option.");

        boolean isValid = false;

        while (!isValid) {
            String input = getInput();
            if (input.equals("1")) {
                feedMoney();
                isValid = true;
            } else if (input.equals("2")) {
                selectProduct();
                isValid = true;
            } else if (input.equals("3")) {
                finishTransaction();
                isValid = true;
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    public void feedMoney() {
        boolean isValid = false;

        while (!isValid) {
            System.out.println("Please insert bill");
            double moneyInput = Double.parseDouble(kb.nextLine());
            if (moneyInput == 1.0 || moneyInput == 2.0 || moneyInput == 5.0 || moneyInput == 10.0 || moneyInput == 20.0 || moneyInput == 50.0 || moneyInput == 100.0) {
                this.moneyAdded += moneyInput;
                try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))) {
                    pw.println(getDate() + " FEED MONEY: \\$" + moneyInput + " \\$" + getMoneyAdded());
                } catch (IOException e) {
                    System.out.println("Couldn't open the file!");
                }
                purchaseMenu();
                isValid = true;
            } else {
                System.out.println("Not a valid bill. Please try again.");
            }
        }
    }

    public void selectProduct() {
        vm.displayItemMenu();

        boolean isValid = false;
        while (!isValid) {
            String choice = getInput();
            if (vm.getItemMap().containsKey(choice)) {
                if (vm.getItemMap().get(choice).getStock() > 0) {
                    if (getMoneyAdded() - vm.getItemMap().get(choice).getCost() >= 0) {
                        try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))) {
                            pw.println(getDate() + " " + vm.getItemMap().get(choice).getName() + " \\$" + getMoneyAdded() + " \\$" + (getMoneyAdded() - vm.getItemMap().get(choice).getCost()));
                        } catch (IOException e) {
                            System.out.println("Couldn't open the file!");
                        }
                        vm.getItemMap().get(choice).decrementStock();
                        System.out.println("Dispensing: " + vm.getItemMap().get(choice).getName() + " " + vm.getItemMap().get(choice).getSlot());
                        this.moneyAdded -= vm.getItemMap().get(choice).getCost();
                        System.out.println("Balance remaining: " + getMoneyAdded());

                    } else {
                        purchaseMenu();
                    }
                } else {
                    System.out.println("Item is sold out!");
                    purchaseMenu();
                }

                if (vm.getItemMap().get(choice).getType().equals("Chip")) {
                    System.out.println("Crunch Crunch, Yum!");
                } else if (vm.getItemMap().get(choice).getType().equals("Candy")) {
                    System.out.println("Munch Munch, Yum!");
                } else if (vm.getItemMap().get(choice).getType().equals("Drink")) {
                    System.out.println("Glug Glug, Yum!");
                } else {vm.getItemMap().get(choice).getName();
                    System.out.println("Chew Chew, Yum!");
                }

                purchaseMenu();

                isValid = true;
            } else {
                System.out.println("Product code does not exist. Returning to Purchase Menu.");
                purchaseMenu();
            }
        }
    }

    public void finishTransaction() {

        try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))) {
            pw.println(getDate() + " GIVE CHANGE: \\$" + getMoneyAdded() + " \\$0.00");
        } catch (IOException e) {
            System.out.println("Couldn't open the file!");
        }

        double change = getMoneyAdded() * 100;

        System.out.print("Your change is ");

        int quarters = (int)(change / 25);
        change = change % 25;

        System.out.print(quarters + " quarters, ");

        int dimes = (int)(change / 10);
        change = change % 10;
        System.out.print(dimes + " dimes, ");

        int nickels = (int)(change / 5);
        change = change % 5;
        System.out.print(nickels + " nickels, and ");

        int pennies = (int)change;
        System.out.print(pennies + " pennies.");

        moneyAdded = change;
        System.out.println("\n\nBalance remaining: " + getMoneyAdded());
        mainMenu();
    }
}