package io.sphere.sdk.shippingmethods;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = PriceFunctionImpl.class)
@HasBuilder(
        factoryMethods = @FactoryMethod(parameterNames = {"currencyCode","function"})
)
public interface PriceFunction {

    String getCurrencyCode();

    String getFunction();
}
