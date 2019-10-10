package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

public interface CustomerInStoreByIdGet extends MetaModelGetDsl<Customer, Customer, CustomerInStoreByIdGet, CustomerExpansionModel<Customer>> {
    
    static CustomerInStoreByIdGet of(final String storeKey, final String id) {
        return new CustomerInStoreByIdGetImpl(storeKey, id);
    }
    
}
