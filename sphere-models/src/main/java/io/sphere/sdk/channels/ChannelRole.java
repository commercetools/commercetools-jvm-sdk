package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum ChannelRole implements SphereEnumeration {
    INVENTORY_SUPPLY, ORDER_EXPORT, ORDER_IMPORT, PRIMARY, PRODUCT_DISTRIBUTION;

    @JsonCreator
    public static ChannelRole ofSphereValue(final String value) {
        return SphereEnumeration.find(values(), value);
    }
}
