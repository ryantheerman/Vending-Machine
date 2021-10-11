package com.techelevator;

public class Item {
    private final String name;
    private final String type;
    private final double price;
    private int numberOfItem;

    public Item(String productName, double price, String type) {
        this(productName, price, type, 5);
    }

    public Item(String productName, double price, String type, int numberOfItem) {
        this.name = productName;
        this.price = price;
        this.type = type;
        this.numberOfItem = numberOfItem;
    }

    public void decrementNumberOfItem() {
        if(numberOfItem <= 0) {
            throw new RuntimeException();
        }

        numberOfItem--;
    }

    public boolean isInStock() {
        return getNumberOfItem() > 0;
    }

    public String toString() {
        return "product: " + getName() + " | " + Display.moneyFormat("price", getPrice()) + " | type: " + getType() + " | quantity: " + getNumberOfItem();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }
}
