package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;

final class CustomerSignInResultExpansionModelImpl<T> extends ExpansionModelImpl<T> implements CustomerSignInResultExpansionModel<T> {
    CustomerSignInResultExpansionModelImpl() {
        super();
    }

    @Override
    public CustomerExpansionModel<T> customer() {
        return CustomerExpansionModel.of(null, "customer");
    }

    @Override
    public CartExpansionModel<T> cart() {
        return CartExpansionModel.of(null, "cart");
    }
}
