package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = CartClassificationImpl.class)
public interface CartClassification extends ShippingRatePriceTier {

    @Override
    default String getType(){
        return "CartClassification";
    }

    String getValue();

}
