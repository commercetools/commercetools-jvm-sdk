package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

class CartInStoreQueryImpl extends MetaModelQueryDslImpl<Cart, CartInStoreQuery, CartQueryModel, CartExpansionModel<Cart>> implements CartInStoreQuery {

    CartInStoreQueryImpl(final String storeKey){
        super("/in-store/key=" + storeKey + "/carts", CartInStoreQuery.resultTypeReference(), CartQueryModel.of(), CartExpansionModel.of(), CartInStoreQueryImpl::new);
    }

    private CartInStoreQueryImpl(final MetaModelQueryDslBuilder<Cart, CartInStoreQuery, CartQueryModel, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
    
}
