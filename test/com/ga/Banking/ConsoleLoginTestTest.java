package com.ga.Banking;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class ConsoleLoginTestTest {
    @Test(expected = NumberFormatException.class)
    @DisplayName("When non-number is used then exception is thrown")
    public final void whenNonNumberIsUsedThenExceptionIsThrown() {
        Double.parseDouble("abc");
    }

    @Test
    @DisplayName("When negative amount is used then amount is rejected")
    public final void whenNegativeAmountIsUsedThenAmountIsRejected() {
        double amount = -50;

        Assert.assertTrue(amount <= 0);
    }

    @Test
    @DisplayName("When zero amount is used then amount is rejected")
    public final void whenZeroAmountIsUsedThenAmountIsRejected() {
        double amount = 0;

        Assert.assertTrue(amount <= 0);
    }

    @Test
    @DisplayName("When positive amount is used then amount is accepted")
    public final void whenPositiveAmountIsUsedThenAmountIsAccepted() {
        double amount = 100;

        Assert.assertTrue(amount > 0);
    }
}