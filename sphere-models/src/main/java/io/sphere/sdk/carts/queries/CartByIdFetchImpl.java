package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class CartByIdFetchImpl extends MetaModelFetchDslImpl<Cart, Cart, CartByIdFetch, CartExpansionModel<Cart>> implements CartByIdFetch {
    CartByIdFetchImpl(final String id) {
        super(id, CartEndpoint.ENDPOINT, CartExpansionModel.of(), CartByIdFetchImpl::new);
    }

    public CartByIdFetchImpl(MetaModelFetchDslBuilder<Cart, Cart, CartByIdFetch, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
