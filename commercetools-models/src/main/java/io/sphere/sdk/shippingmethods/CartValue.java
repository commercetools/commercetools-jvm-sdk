package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = CartValueImpl.class)
public interface CartValue extends ShippingRatePriceTier {

    @Override
    default String getType(){
        return "CartValue";
    }

    Long getMinimumCentAmount();

}
