package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

public class CustomerExpansionModel<T> extends ExpansionModel<T> {
    private CustomerExpansionModel() {
        super();
    }

    public static CustomerExpansionModel<Customer> of(){
        return new CustomerExpansionModel<>();
    }

    public ExpansionPath<T> customerGroup() {
        return ExpansionPath.of("customerGroup");
    }
}
