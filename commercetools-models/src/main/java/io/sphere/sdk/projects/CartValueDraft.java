package io.sphere.sdk.projects;


import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {}))
public interface CartValueDraft extends ShippingRateInputTypeDraft{

}
