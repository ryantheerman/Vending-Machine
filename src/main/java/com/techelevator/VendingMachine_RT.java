package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine_RT {

    private Map<String, Item_RT> itemMap= new LinkedHashMap<>();

    public VendingMachine_RT() {
        stockMachine();
    }

    public Map<String, Item_RT> getItemMap() {
        return itemMap;
    }

    public void stockMachine() {
        File inventory = new File("vendingmachine.csv");
        try {
            Scanner inventoryFile = new Scanner(inventory);

            // converts file data to String array
            while(inventoryFile.hasNextLine()) {
                String line = inventoryFile.nextLine();
                String[] itemArray = line.split("\\|");

                Item_RT item = new Item_RT(itemArray[0], itemArray[1], Double.parseDouble(itemArray[2]), itemArray[3]);
                itemMap.put(itemArray[0], item);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        }
    }

    public void displayItemMenu() {
        for (String key : itemMap.keySet()) {
            System.out.println(key + " " + itemMap.get(key).getName() + " " + itemMap.get(key).getCost() +
                    " Stock (" + itemMap.get(key).getStock() + ")"); // to string
        }
    }
}