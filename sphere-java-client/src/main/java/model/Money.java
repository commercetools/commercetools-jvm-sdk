package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;

@ThreadSafe
public class Money {

    private final int centAmount;
    private final String currencyCode;

    public int getCentAmount() { return centAmount; }
    public String getCurrencyCode() { return currencyCode; }

    public Money(int centAmount, String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return (this.centAmount / 100) + " " + this.currencyCode;
    }
}
