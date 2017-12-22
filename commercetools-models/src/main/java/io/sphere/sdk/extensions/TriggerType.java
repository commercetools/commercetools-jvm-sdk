package io.sphere.sdk.extensions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum TriggerType implements SphereEnumeration{

    CREATE,
    UPDATE;

    @JsonCreator
    public static TriggerType ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }

    @Override
    public String toSphereName() {
        return name().toLowerCase();
    }

}
