package io.sphere.sdk.projects;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedEnumValue;

import java.util.Set;

@ResourceValue
@JsonDeserialize(as = CartClassificationImpl.class)
public interface CartClassification extends ShippingRateInputType{

    Set<LocalizedEnumValue> getValues();

}
