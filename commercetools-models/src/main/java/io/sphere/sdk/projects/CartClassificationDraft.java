package io.sphere.sdk.projects;


import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import java.util.Set;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"values"}))
public interface CartClassificationDraft extends ShippingRateInputTypeDraft{


    Set<CartClassificationEntry> getValues();

}
