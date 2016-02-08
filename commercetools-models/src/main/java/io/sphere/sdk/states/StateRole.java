package io.sphere.sdk.states;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum StateRole implements SphereEnumeration {
    REVIEW_INCLUDED_IN_STATISTICS;

    @JsonCreator
    public static StateRole ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
