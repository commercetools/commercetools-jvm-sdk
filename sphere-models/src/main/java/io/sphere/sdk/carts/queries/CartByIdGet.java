package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathsHolder;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 Gets a cart by ID.

 {@include.example io.sphere.sdk.carts.queries.CartByIdGetTest#fetchById()}
 */
public interface CartByIdGet extends MetaModelGetDsl<Cart, Cart, CartByIdGet, CartExpansionModel<Cart>> {
    static CartByIdGet of(final Identifiable<Cart> cart) {
        return of(cart.getId());
    }

    static CartByIdGet of(final String id) {
        return new CartByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Cart>> expansionPaths();

    @Override
    CartByIdGet plusExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartByIdGet withExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartByIdGet withExpansionPaths(final List<ExpansionPath<Cart>> expansionPaths);
}
