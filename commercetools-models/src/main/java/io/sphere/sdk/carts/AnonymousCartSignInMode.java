package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 *
 * Mode to handle an anonymous cart while signing-in.
 */
public enum AnonymousCartSignInMode implements SphereEnumeration {

    /**
     * LineItems of the anonymous cart will be copied to the customer’s cart.
     *
     * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandIntegrationTest#signInWithAnonymousCartMergeWithExistingCustomerCart()}
     *
     */
    MERGE_WITH_EXISTING_CUSTOMER_CART,

    /**
     * The anonymous cart is used as new active customer cart. No LineItems get merged.
     *
     * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandIntegrationTest#signInWithAnonymousCartUseAsNewActiveCustomerCart()}
     */
    USE_AS_NEW_ACTIVE_CUSTOMER_CART;

    @JsonCreator
    public static AnonymousCartSignInMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
