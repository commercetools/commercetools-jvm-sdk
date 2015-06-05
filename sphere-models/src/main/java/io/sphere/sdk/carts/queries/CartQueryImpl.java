package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;

/**

 {@doc.gen summary carts}

 */
final class CartQueryImpl extends UltraQueryDslImpl<Cart, CartQuery, CartQueryModel<Cart>, CartExpansionModel<Cart>> implements CartQuery {
    CartQueryImpl(){
        super(CartEndpoint.ENDPOINT.endpoint(), CartQuery.resultTypeReference(), CartQueryModel.of(), CartExpansionModel.of(), CartQueryImpl::new);
    }

    private CartQueryImpl(final UltraQueryDslBuilder<Cart, CartQuery, CartQueryModel<Cart>, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}