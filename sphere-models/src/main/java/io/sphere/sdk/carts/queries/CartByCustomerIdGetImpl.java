package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import java.util.Collections;

final class CartByCustomerIdGetImpl extends MetaModelGetDslImpl<Cart, Cart, CartByCustomerIdGet, CartExpansionModel<Cart>> implements CartByCustomerIdGet {

    CartByCustomerIdGetImpl(final String customerId) {
        super(CartEndpoint.ENDPOINT, "", CartExpansionModel.of(), CartByCustomerIdGetImpl::new, Collections.singletonList(HttpQueryParameter.of("customerId", customerId)));
    }

    public CartByCustomerIdGetImpl(MetaModelFetchDslBuilder<Cart, Cart, CartByCustomerIdGet, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
