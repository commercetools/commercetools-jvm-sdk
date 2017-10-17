package io.sphere.sdk.projects;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = CartValueImpl.class)
public interface CartValue extends ShippingRateInputType{

}
