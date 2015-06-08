package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

import static java.util.Arrays.asList;

final class CartByCustomerIdFetchImpl extends MetaModelFetchDslImpl<Cart, CartByCustomerIdFetch, CartExpansionModel<Cart>> implements CartByCustomerIdFetch {

    CartByCustomerIdFetchImpl(final String customerId) {
        super(CartEndpoint.ENDPOINT, "", CartExpansionModel.of(), CartByCustomerIdFetchImpl::new, asList(HttpQueryParameter.of("customerId", customerId)));
    }

    public CartByCustomerIdFetchImpl(MetaModelFetchDslBuilder<Cart, CartByCustomerIdFetch, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
