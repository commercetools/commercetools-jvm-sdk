package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.expansion.ExpansionModel;

final class CustomerSignInResultExpansionModelImpl<T> extends ExpansionModel<T> implements CustomerSignInResultExpansionModel<T> {
    CustomerSignInResultExpansionModelImpl() {
        super();
    }

    @Override
    public CustomerExpansionModel<T> customer() {
        return new CustomerExpansionModel<>(null, "customer");
    }
}
