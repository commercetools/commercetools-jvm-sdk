package io.sphere.sdk.products;

import io.sphere.sdk.carts.TaxedItemPrice;

import javax.money.MonetaryAmount;

public class TaxedItemPriceTestImpl implements TaxedItemPrice{

    private final MonetaryAmount totalNet;
    private final MonetaryAmount totalGross;

    protected TaxedItemPriceTestImpl(final MonetaryAmount totalNet, final MonetaryAmount totalGross){
        this.totalNet = totalNet;
        this.totalGross = totalGross;
    }

    public static TaxedItemPriceTestImpl of(final MonetaryAmount totalNet, final MonetaryAmount totalGross){
        return new TaxedItemPriceTestImpl( totalNet, totalGross);
    }

    @Override
    public MonetaryAmount getTotalNet() {
        return totalNet;
    }

    @Override
    public MonetaryAmount getTotalGross() {
        return totalGross;
    }
}
