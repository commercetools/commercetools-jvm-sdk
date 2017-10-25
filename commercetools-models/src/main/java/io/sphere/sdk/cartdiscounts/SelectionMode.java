package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * Determines which of the items, participating in a {@link MultiBuyLineItemsTarget} and a {@link MultiBuyCustomLineItemsTarget},
 * are selected to be discounted. This referres to the current price including already applied discounts.
 */
public enum SelectionMode  implements SphereEnumeration {
    /**
     * Select the cheapest items to be discounted.
     */
    CHEAPEST,
    /**
     * Select the most expensive items to be discounted.
     */
    MOST_EXPENSIVE;

    @JsonCreator
    public static SelectionMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
