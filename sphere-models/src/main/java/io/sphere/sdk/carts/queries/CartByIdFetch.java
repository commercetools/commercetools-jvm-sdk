package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

/**
 Gets a cart by ID.

 {@include.example io.sphere.sdk.carts.queries.CartByIdFetchTest#fetchById()}
 */
public interface CartByIdFetch extends MetaModelFetchDsl<Cart, CartByIdFetch, CartExpansionModel<Cart>> {
    static CartByIdFetch of(final Identifiable<Cart> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CartByIdFetch of(final String id) {
        return new CartByIdFetchImpl(id);
    }
}
