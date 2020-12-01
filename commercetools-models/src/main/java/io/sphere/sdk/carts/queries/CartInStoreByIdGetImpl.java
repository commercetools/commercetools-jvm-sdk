package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CartInStoreByIdGetImpl extends MetaModelGetDslImpl<Cart, Cart, CartInStoreByIdGet, CartExpansionModel<Cart>> implements CartInStoreByIdGet {

    CartInStoreByIdGetImpl(final String storeKey, final String cartId) {
        super(cartId, JsonEndpoint.of(Cart.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/carts"), CartExpansionModel.of(), CartInStoreByIdGetImpl::new);
    }

    public CartInStoreByIdGetImpl(final MetaModelGetDslBuilder<Cart, Cart, CartInStoreByIdGet, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
