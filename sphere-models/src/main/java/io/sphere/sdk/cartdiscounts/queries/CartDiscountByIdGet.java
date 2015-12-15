package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathsHolder;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdGetTest#execution()}
 */
public interface CartDiscountByIdGet extends MetaModelGetDsl<CartDiscount, CartDiscount, CartDiscountByIdGet, CartDiscountExpansionModel<CartDiscount>> {
    static CartDiscountByIdGet of(final Identifiable<CartDiscount> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CartDiscountByIdGet of(final String id) {
        return new CartDiscountByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<CartDiscount>> expansionPaths();

    @Override
    CartDiscountByIdGet plusExpansionPaths(final ExpansionPath<CartDiscount> expansionPath);

    @Override
    CartDiscountByIdGet withExpansionPaths(final ExpansionPath<CartDiscount> expansionPath);

    @Override
    CartDiscountByIdGet withExpansionPaths(final List<ExpansionPath<CartDiscount>> expansionPaths);
}
