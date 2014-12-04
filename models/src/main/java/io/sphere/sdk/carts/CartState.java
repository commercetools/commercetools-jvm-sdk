package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum CartState implements SphereEnumeration {
    /**
     The cart can be updated and ordered. It is the default state.
     */
    ACTIVE,
    /**
     Anonymous cart whose content was merged into a customers cart on signin. No further operations on the cart are allowed.
     */
    MERGED;

    public static CartState defaultValue() {
        return CartState.ACTIVE;
    }

    @JsonCreator
    public static CartState ofSphereValue(final String value) {
        return SphereEnumeration.find(values(), value);
    }
}
