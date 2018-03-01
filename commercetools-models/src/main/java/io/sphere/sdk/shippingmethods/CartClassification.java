package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = CartClassificationImpl.class)
@HasBuilder(factoryMethods = {
        @FactoryMethod(parameterNames = {"value", "price"})
}
)
public interface CartClassification extends ShippingRatePriceTier {

    String getValue();


}
