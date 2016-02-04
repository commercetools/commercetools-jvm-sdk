package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.customers.CustomerSignInResult;

public interface CustomerSignInResultExpansionModel<T> {
    CustomerExpansionModel<T> customer();


    static CustomerSignInResultExpansionModel<CustomerSignInResult> of(){
        return new CustomerSignInResultExpansionModelImpl<>();
    }
}
