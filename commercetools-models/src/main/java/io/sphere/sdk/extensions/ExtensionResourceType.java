package io.sphere.sdk.extensions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * This is a representation of the resource to which the extension is related, it i not a {@link io.sphere.sdk.models.SphereEnumeration}
 * it was added to add tye safety while the developer points to a resource, while extending it.
 *
 */
public enum ExtensionResourceType {

    CART,
    PAYMENT,
    CUSTOMER,
    ORDER;


    @SuppressWarnings("unused")
    @JsonCreator
    public static ExtensionResourceType forValue(String value) {
        return valueOf(value.toUpperCase());
    }

    @SuppressWarnings("unused")
    @JsonValue
    public String toValue() {
        return toString().toLowerCase();
    }


}
