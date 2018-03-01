package io.sphere.sdk.discountcodes;


import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * DiscountCodeState.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 */
public enum DiscountCodeState implements SphereEnumeration {
    /** The discount code is not active or it does not contain any active and valid cart discounts. */
    NOT_ACTIVE,

    /** The discount code is active and it contains at least one active and valid cart discount. But its cart predicate does not match the cart or none of the contained active discount's cart predicates match the cart. */
    DOES_NOT_MATCH_CART,

    /** The discount code is active and it contains at least one active and valid cart discount. The discount code cartPredicate matches the cart and at least one of the contained active discount's cart predicates matches the cart. */
    MATCHES_CART,

    /** maxApplications or maxApplicationsPerCustomer for discountCode has been reached. */
    MAX_APPLICATION_REACHED,

    /** The discount code is active and none of the discounts were applied and at least one of them was not applied because it was stopped by a previous discount that had the {@link io.sphere.sdk.cartdiscounts.StackingMode} */
    APPLICATION_STOPPED_BY_PREVIOUS_DISCOUNT,

    /** The discount code is not valid or it does not contain any valid cart discounts. Validity is determined based on the {@code 'validFrom'} and {@code 'validUntil'} dates. */
    NOT_VALID;

    @JsonCreator
    public static DiscountCodeState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
