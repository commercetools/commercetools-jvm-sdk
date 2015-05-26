package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.ByIdFetchImpl;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdFetchTest#execution()}
 */
public class CartDiscountByIdFetch extends ByIdFetchImpl<CartDiscount> {
    private CartDiscountByIdFetch(final String id) {
        super(id, CartDiscountEndpoint.ENDPOINT);
    }

    public static CartDiscountByIdFetch of(final CartDiscount cartDiscount) {
        return of(cartDiscount.getId());
    }

    public static CartDiscountByIdFetch of(final String id) {
        return new CartDiscountByIdFetch(id);
    }
}
