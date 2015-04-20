package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum ChannelRoles implements SphereEnumeration {
    INVENTORY_SUPPLY, ORDER_EXPORT, ORDER_IMPORT, PRIMARY;

    @JsonCreator
    public static ChannelRoles ofSphereValue(final String value) {
        return SphereEnumeration.find(values(), value);
    }
}
