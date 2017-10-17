package io.sphere.sdk.carts;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"key"}))
public interface ClassificationShippingRateInputDraft extends ShippingRateInputDraft {

    String getKey();

}
