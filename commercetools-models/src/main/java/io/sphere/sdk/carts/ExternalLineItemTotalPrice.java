package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.money.MonetaryAmount;

@JsonDeserialize(as = ExternalLineItemTotalPriceImpl.class)
public interface ExternalLineItemTotalPrice {
    MonetaryAmount getPrice();

    MonetaryAmount getTotalPrice();

    static ExternalLineItemTotalPrice ofPriceAndTotalPrice(final MonetaryAmount price, final MonetaryAmount totalPrice) {
        return new ExternalLineItemTotalPriceImpl(price, totalPrice);
    }
}
