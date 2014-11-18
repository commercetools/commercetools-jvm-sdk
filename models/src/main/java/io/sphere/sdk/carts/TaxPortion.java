package io.sphere.sdk.carts;

import javax.money.MonetaryAmount;

public class TaxPortion {
    private final double rate;
    private final MonetaryAmount money;

    private TaxPortion(final double rate, final MonetaryAmount money) {
        this.rate = rate;
        this.money = money;
    }

    public static TaxPortion of(final double rate, final MonetaryAmount money) {
        return new TaxPortion(rate, money);
    }

    public double getRate() {
        return rate;
    }

    public MonetaryAmount getMoney() {
        return money;
    }
}
