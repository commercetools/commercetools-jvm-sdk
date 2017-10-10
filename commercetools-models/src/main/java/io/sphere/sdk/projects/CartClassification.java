package io.sphere.sdk.projects;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ResourceValue
@JsonDeserialize(as = CartClassificationImpl.class)
public interface CartClassification extends ShippingRateInputType{

    Set<CartClassificationEntry> getValues();

}
