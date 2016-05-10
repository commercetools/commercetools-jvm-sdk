package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.money.MonetaryAmount;

@JsonDeserialize(as = TaxedPriceImpl.class)
public interface TaxedItemPrice {
    MonetaryAmount getTotalNet();

    MonetaryAmount getTotalGross();
}
