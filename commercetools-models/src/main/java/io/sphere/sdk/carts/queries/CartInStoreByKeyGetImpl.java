package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CartInStoreByKeyGetImpl extends MetaModelGetDslImpl<Cart, Cart, CartInStoreByKeyGet, CartExpansionModel<Cart>> implements CartInStoreByKeyGet {

    CartInStoreByKeyGetImpl(final String storeKey, final String key) {
        super("key=" + key, JsonEndpoint.of(Cart.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/carts"), CartExpansionModel.of(), CartInStoreByKeyGetImpl::new);
    }

    public CartInStoreByKeyGetImpl(final MetaModelGetDslBuilder<Cart, Cart, CartInStoreByKeyGet, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
