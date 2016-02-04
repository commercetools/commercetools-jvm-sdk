package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.customers.Customer;

import java.util.List;

public interface CustomerExpansionModel<T> {
    CustomerGroupExpansionModel<T> customerGroup();

    static CustomerExpansionModel<Customer> of(){
        return new CustomerExpansionModelImpl<>();
    }

    static <T> CustomerExpansionModel<T> of(final List<String> parentPath, final String path){
        return new CustomerExpansionModelImpl<>(parentPath, path);
    }
}
