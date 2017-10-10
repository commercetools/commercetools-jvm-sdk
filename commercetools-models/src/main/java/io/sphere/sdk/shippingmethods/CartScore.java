package io.sphere.sdk.shippingmethods;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nullable;

@ResourceValue
@JsonDeserialize(as = CartScoreImpl.class)
public interface CartScore extends ShippingRatePriceTier {

    @Nullable
    Long getScore();

    @Nullable
    PriceFunction getPriceFunction();

}
