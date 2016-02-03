package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.expansion.ExpansionModel;

public final class CustomerSignInResultExpansionModel<T> extends ExpansionModel<T> {
    private CustomerSignInResultExpansionModel() {
        super();
    }

    public static CustomerSignInResultExpansionModel<CustomerSignInResult> of(){
        return new CustomerSignInResultExpansionModel<>();
    }

    public CustomerExpansionModel<CustomerSignInResult> customer() {
        return new CustomerExpansionModel<>(null, "customer");
    }
}
