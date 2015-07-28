package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

import java.util.function.Function;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdFetchTest#execution()}
 */
final class CartDiscountByIdFetchImpl extends MetaModelFetchDslImpl<CartDiscount, CartDiscount, CartDiscountByIdFetch, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountByIdFetch {
    CartDiscountByIdFetchImpl(final String id) {
        super(id, CartDiscountEndpoint.ENDPOINT, CartDiscountExpansionModel.of(), CartDiscountByIdFetchImpl::new);
    }

    public CartDiscountByIdFetchImpl(MetaModelFetchDslBuilder<CartDiscount, CartDiscount, CartDiscountByIdFetch, CartDiscountExpansionModel<CartDiscount>> builder) {
        super(builder);
    }
}
