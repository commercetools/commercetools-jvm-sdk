package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

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

    @Override
    CartByIdFetch plusExpansionPaths(final Function<CartExpansionModel<Cart>, ExpansionPath<Cart>> m);

    @Override
    CartByIdFetch withExpansionPaths(final Function<CartExpansionModel<Cart>, ExpansionPath<Cart>> m);

    @Override
    List<ExpansionPath<Cart>> expansionPaths();

    @Override
    CartByIdFetch plusExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartByIdFetch withExpansionPaths(final ExpansionPath<Cart> expansionPath);

    @Override
    CartByIdFetch withExpansionPaths(final List<ExpansionPath<Cart>> expansionPaths);
}
