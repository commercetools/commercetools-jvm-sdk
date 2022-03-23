package io.sphere.sdk.productselections;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum ProductSelectionType {
    INDIVIDUAL;

    public static ProductSelectionType defaultValue() {
        return INDIVIDUAL;
    }

    @JsonCreator
    public static ProductSelectionType ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
