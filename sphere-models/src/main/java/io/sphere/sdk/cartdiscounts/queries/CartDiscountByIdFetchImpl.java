package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdFetchTest#execution()}
 */
public class CartDiscountByIdFetchImpl extends MetaModelFetchDslImpl<CartDiscount, CartDiscountByIdFetch, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountByIdFetch {
    CartDiscountByIdFetchImpl(final String id) {
        super(id, CartDiscountEndpoint.ENDPOINT, CartDiscountExpansionModel.of(), CartDiscountByIdFetchImpl::new);
    }

    public CartDiscountByIdFetchImpl(MetaModelFetchDslBuilder<CartDiscount, CartDiscountByIdFetch, CartDiscountExpansionModel<CartDiscount>> builder) {
        super(builder);
    }
}
