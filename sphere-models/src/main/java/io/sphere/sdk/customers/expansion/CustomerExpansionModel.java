package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.expansion.ExpansionModel;

import javax.annotation.Nullable;

public final class CustomerExpansionModel<T> extends ExpansionModel<T> {
    private CustomerExpansionModel() {
        super();
    }

    CustomerExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public static CustomerExpansionModel<Customer> of(){
        return new CustomerExpansionModel<>();
    }

    public CustomerGroupExpansionModel<T> customerGroup() {
        return CustomerGroupExpansionModel.of(buildPathExpression(), "customerGroup");
    }
}
