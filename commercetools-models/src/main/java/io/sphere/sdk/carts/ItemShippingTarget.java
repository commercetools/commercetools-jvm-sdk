package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = ItemShippingTargetImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = {"addressKey","quantity"}))
public interface ItemShippingTarget {

    String getAddressKey();

    Long getQuantity();

}
