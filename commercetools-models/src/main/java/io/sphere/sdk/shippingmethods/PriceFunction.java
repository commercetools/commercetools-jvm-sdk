package io.sphere.sdk.shippingmethods;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = PriceFunctionImpl.class)
public interface PriceFunction {

    String getCurrencyCode();

    String getFunction();
}
