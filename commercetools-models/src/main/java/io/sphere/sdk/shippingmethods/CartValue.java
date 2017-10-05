package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.products.Price;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

@ResourceValue
@JsonDeserialize(as = CartValueImpl.class)
public interface CartValue extends ShippingRatePriceTier {

    @Override
    default String getType() {
        return "CartValue";
    }

    Long getMinimumCentAmount();

    static CartValue of(final Long minimumCentAmount, final MonetaryAmount price) {
        return new CartValueImpl(null, minimumCentAmount, price);
    }

}
