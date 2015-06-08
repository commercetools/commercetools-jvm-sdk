package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

/**

 {@doc.gen summary cart discounts}

 */
final class CartDiscountQueryImpl extends MetaModelQueryDslImpl<CartDiscount, CartDiscountQuery, CartDiscountQueryModel<CartDiscount>, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountQuery {
    CartDiscountQueryImpl(){
        super(CartDiscountEndpoint.ENDPOINT.endpoint(), CartDiscountQuery.resultTypeReference(), CartDiscountQueryModel.of(), CartDiscountExpansionModel.of(), CartDiscountQueryImpl::new);
    }

    private CartDiscountQueryImpl(final MetaModelQueryDslBuilder<CartDiscount, CartDiscountQuery, CartDiscountQueryModel<CartDiscount>, CartDiscountExpansionModel<CartDiscount>> builder) {
        super(builder);
    }
}