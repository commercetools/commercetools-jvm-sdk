package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartEndpoint;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import java.util.Collections;

final class CartByCustomerIdGetImpl extends MetaModelGetDslImpl<Cart, Cart, CartByCustomerIdGet, CartExpansionModel<Cart>> implements CartByCustomerIdGet {

    CartByCustomerIdGetImpl(final String customerId) {
        super(CartEndpoint.ENDPOINT, "", CartExpansionModel.of(), CartByCustomerIdGetImpl::new, Collections.singletonList(NameValuePair.of("customerId", customerId)));
    }

    public CartByCustomerIdGetImpl(MetaModelGetDslBuilder<Cart, Cart, CartByCustomerIdGet, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
