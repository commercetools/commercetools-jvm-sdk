package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

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

    @Override
    CartDiscountByIdFetch plusExpansionPaths(final Function<CartDiscountExpansionModel<CartDiscount>, ExpansionPath<CartDiscount>> m);

    @Override
    CartDiscountByIdFetch withExpansionPaths(final Function<CartDiscountExpansionModel<CartDiscount>, ExpansionPath<CartDiscount>> m);

    @Override
    List<ExpansionPath<CartDiscount>> expansionPaths();

    @Override
    CartDiscountByIdFetch plusExpansionPaths(final ExpansionPath<CartDiscount> expansionPath);

    @Override
    CartDiscountByIdFetch withExpansionPaths(final ExpansionPath<CartDiscount> expansionPath);

    @Override
    CartDiscountByIdFetch withExpansionPaths(final List<ExpansionPath<CartDiscount>> expansionPaths);
}
