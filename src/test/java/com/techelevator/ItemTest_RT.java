package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

public class ItemTest_RT {

    @Test
    public void decrements_stock_by_one() {
        String slot = "slot";
        String name = "name";
        Double cost = 0.0;
        String type = "type";
        Item_RT item = new Item_RT(slot, name, cost, type);

        item.decrementStock();

        Assert.assertEquals(4, item.getStock());
    }
}