package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

/**

 {@doc.gen summary carts}

 */
final class CartQueryImpl extends MetaModelQueryDslImpl<Cart, CartQuery, CartQueryModel, CartExpansionModel<Cart>> implements CartQuery {
    CartQueryImpl(){
        super(CartEndpoint.ENDPOINT.endpoint(), CartQuery.resultTypeReference(), CartQueryModel.of(), CartExpansionModel.of(), CartQueryImpl::new);
    }

    private CartQueryImpl(final MetaModelQueryDslBuilder<Cart, CartQuery, CartQueryModel, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}