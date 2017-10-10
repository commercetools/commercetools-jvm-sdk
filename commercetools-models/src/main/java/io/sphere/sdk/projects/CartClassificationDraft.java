package io.sphere.sdk.projects;


import io.sphere.sdk.annotations.ResourceValue;

import java.util.Set;

@ResourceValue
public interface CartClassificationDraft extends ShippingRateInputTypeDraft{


    Set<CartClassificationEntry> getValues();

}
