package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CartInStoreByCustomerIdGetImpl extends MetaModelGetDslImpl<Cart, Cart, CartInStoreByCustomerIdGet, CartExpansionModel<Cart>> implements CartInStoreByCustomerIdGet {

    CartInStoreByCustomerIdGetImpl(final String storeKey, final String customerId) {
        super("?customerId=" + customerId, JsonEndpoint.of(Cart.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/carts"), CartExpansionModel.of(), CartInStoreByCustomerIdGetImpl::new);
    }

    public CartInStoreByCustomerIdGetImpl(final MetaModelGetDslBuilder<Cart, Cart, CartInStoreByCustomerIdGet, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
