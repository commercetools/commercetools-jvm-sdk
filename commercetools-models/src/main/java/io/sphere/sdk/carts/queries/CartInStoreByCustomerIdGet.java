package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

public interface CartInStoreByCustomerIdGet extends MetaModelGetDsl<Cart, Cart, CartInStoreByCustomerIdGet, CartExpansionModel<Cart>> {
    
    static CartInStoreByCustomerIdGet of(final String storeKey, final String customerId) {
        return new CartInStoreByCustomerIdGetImpl(storeKey, customerId);
    }
}
