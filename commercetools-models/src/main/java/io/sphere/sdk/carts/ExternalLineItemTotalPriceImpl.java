package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;

final class ExternalLineItemTotalPriceImpl extends Base implements ExternalLineItemTotalPrice {
    private final MonetaryAmount price;
    private final MonetaryAmount totalPrice;

    @JsonCreator
    public ExternalLineItemTotalPriceImpl(final MonetaryAmount price, final MonetaryAmount totalPrice) {
        this.price = price;
        this.totalPrice = totalPrice;
    }

    @Override
    public MonetaryAmount getPrice() {
        return price;
    }

    @Override
    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }
}
