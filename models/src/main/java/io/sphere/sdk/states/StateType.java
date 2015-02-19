package io.sphere.sdk.states;

import io.sphere.sdk.models.SphereEnumeration;

public enum StateType implements SphereEnumeration {

    LINE_ITEM_STATE;

    @JsonCreator
    public static StateType ofSphereValue(final String value) {
        return SphereEnumeration.find(values(), value);
    }

}
