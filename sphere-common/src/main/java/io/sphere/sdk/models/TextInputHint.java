package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TextInputHint implements SphereEnumeration {
    SINGLE_LINE, MULTI_LINE;

    @JsonCreator
    public static TextInputHint ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
