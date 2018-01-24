package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * An enumeration that allows to track cart's origin.
 */
public enum CartOrigin implements SphereEnumeration{


    /**
     * The cart was created by the customer. This is the default value.
     */
    CUSTOMER,
    /**
     * The cart was created on behalf of the customer by a customer representative in the Merchant Center or a similar tool of the Merchant.
     */
    MERCHANT;


    public static CartOrigin defaultValue() {
        return CUSTOMER;
    }

    @JsonCreator
    public static CartOrigin ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
