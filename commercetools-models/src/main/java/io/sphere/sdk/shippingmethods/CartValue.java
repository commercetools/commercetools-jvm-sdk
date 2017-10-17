package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = CartValueImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = {"minimumCentAmount","price"}))
public interface CartValue extends ShippingRatePriceTier {

    Long getMinimumCentAmount();

}
