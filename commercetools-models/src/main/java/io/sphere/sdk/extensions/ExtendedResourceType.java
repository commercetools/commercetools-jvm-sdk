package io.sphere.sdk.extensions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExtendedResourceType {

    CART,
    PAYMENT,
    CUSTOMER;

    @JsonCreator
    public static ExtendedResourceType forValue(String value) {
        return valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return toString().toLowerCase();
    }


}
