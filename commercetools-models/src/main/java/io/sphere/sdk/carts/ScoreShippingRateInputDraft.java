package io.sphere.sdk.carts;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"score"}))
public interface ScoreShippingRateInputDraft extends ShippingRateInputDraft{

    Long getScore();

}
