package com.techelevator;

public class Item_RT {

    private String slot;
    private String name;
    private Double cost;
    private String type;
    private int stock;

    public Item_RT(String slot, String name, Double cost, String type) {
        this.slot = slot;
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.stock = 5;
    }

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public Double getCost() {
        return cost;
    }

    public String getType() {
        return type;
    }

    public int getStock() {
        return stock;
    }

    public int decrementStock() {
        return stock--;
    }
}
