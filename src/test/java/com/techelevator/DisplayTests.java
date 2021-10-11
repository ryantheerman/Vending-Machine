package com.techelevator;

import org.junit.Test;
import org.junit.Assert;

public class DisplayTests {

    @Test
    public void moneyFormat_returns_correct_result_given_amount_and_235() {
        Assert.assertEquals("amount: $2.35", Display.moneyFormat("amount", 2.35));
    }
}
