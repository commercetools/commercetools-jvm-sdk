package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;

public class CustomerExpansionModel<T> extends ExpansionModel<T> {
    private CustomerExpansionModel() {
        super();
    }

    CustomerExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public static CustomerExpansionModel<Customer> of(){
        return new CustomerExpansionModel<>();
    }

    public ExpansionPathContainer<T> customerGroup() {
        return expansionPath("customerGroup");
    }
}
