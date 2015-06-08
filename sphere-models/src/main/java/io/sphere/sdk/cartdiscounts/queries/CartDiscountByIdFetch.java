package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdFetchTest#execution()}
 */
public interface CartDiscountByIdFetch extends MetaModelFetchDsl<CartDiscount, CartDiscountByIdFetch, CartDiscountExpansionModel<CartDiscount>> {
    static CartDiscountByIdFetch of(final Identifiable<CartDiscount> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CartDiscountByIdFetch of(final String id) {
        return new CartDiscountByIdFetchImpl(id);
    }
}
