package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class CartByIdGetImpl extends MetaModelGetDslImpl<Cart, Cart, CartByIdGet, CartExpansionModel<Cart>> implements CartByIdGet {
    CartByIdGetImpl(final String id) {
        super(id, CartEndpoint.ENDPOINT, CartExpansionModel.of(), CartByIdGetImpl::new);
    }

    public CartByIdGetImpl(MetaModelGetDslBuilder<Cart, Cart, CartByIdGet, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
