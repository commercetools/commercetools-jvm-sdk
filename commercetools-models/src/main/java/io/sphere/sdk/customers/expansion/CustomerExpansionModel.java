package io.sphere.sdk.customers.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

public interface CustomerExpansionModel<T> extends ExpansionPathContainer<T> {
    CustomerGroupExpansionModel<T> customerGroup();

    static CustomerExpansionModel<Customer> of(){
        return new CustomerExpansionModelImpl<>();
    }

    static <T> CustomerExpansionModel<T> of(final List<String> parentPath, final String path){
        return new CustomerExpansionModelImpl<>(parentPath, path);
    }
}
