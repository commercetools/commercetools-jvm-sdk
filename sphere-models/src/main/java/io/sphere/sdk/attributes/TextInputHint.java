package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum TextInputHint implements SphereEnumeration {
    SINGLE_LINE, MULTI_LINE;

    @JsonCreator
    public static TextInputHint ofSphereValue(final String value) {
        return SphereEnumeration.find(values(), value);
    }
}
