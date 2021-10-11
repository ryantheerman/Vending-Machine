package com.techelevator;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ItemTests {
    private Item mountainMelter;

    @Before
    public void setup() {
        mountainMelter = new Item("Mountain Melter", 1.50, "Drink");
    }

    @Test
    public void toString_returns_correct_String() {
        Assert.assertEquals("product: Mountain Melter | price: $1.50 | type: Drink | quantity: 5", mountainMelter.toString());
    }

    @Test
    public void decrementNumberOfItem_works() {
        mountainMelter.decrementNumberOfItem();
        Assert.assertEquals(4, mountainMelter.getNumberOfItem());
    }

    @Test
    public void isInStock_returns_true_when_no_action_is_taken() {
        Assert.assertTrue(mountainMelter.isInStock());
    }

    @Test
    public void isInStock_returns_false_when_number_of_item_decremented_five_times() {
        mountainMelter.decrementNumberOfItem();
        mountainMelter.decrementNumberOfItem();
        mountainMelter.decrementNumberOfItem();
        mountainMelter.decrementNumberOfItem();
        mountainMelter.decrementNumberOfItem();
        Assert.assertFalse(mountainMelter.isInStock());
    }
}
