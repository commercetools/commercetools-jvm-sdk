package io.sphere.sdk.extensions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;
import org.apache.commons.lang3.StringUtils;

public enum TriggerType implements SphereEnumeration {

    CREATE,

    UPDATE;

    @JsonCreator
    public static TriggerType TriggerType(final String value) {
        return SphereEnumeration.findBySphereName(values(), StringUtils.capitalize(value)).get();
    }

    @Override
    public String toSphereName() {
        return name().toLowerCase();
    }
}
