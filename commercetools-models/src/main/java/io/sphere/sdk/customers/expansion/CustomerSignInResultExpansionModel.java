package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.customers.CustomerSignInResult;

public interface CustomerSignInResultExpansionModel<T> {
    CustomerExpansionModel<T> customer();

    CartExpansionModel<T> cart();

    static CustomerSignInResultExpansionModel<CustomerSignInResult> of() {
        return new CustomerSignInResultExpansionModelImpl<>();
    }
}
