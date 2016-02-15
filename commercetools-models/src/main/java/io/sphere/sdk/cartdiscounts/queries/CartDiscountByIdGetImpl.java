package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdGetIntegrationTest#execution()}
 */
final class CartDiscountByIdGetImpl extends MetaModelGetDslImpl<CartDiscount, CartDiscount, CartDiscountByIdGet, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountByIdGet {
    CartDiscountByIdGetImpl(final String id) {
        super(id, CartDiscountEndpoint.ENDPOINT, CartDiscountExpansionModel.of(), CartDiscountByIdGetImpl::new);
    }

    public CartDiscountByIdGetImpl(MetaModelGetDslBuilder<CartDiscount, CartDiscount, CartDiscountByIdGet, CartDiscountExpansionModel<CartDiscount>> builder) {
        super(builder);
    }
}
