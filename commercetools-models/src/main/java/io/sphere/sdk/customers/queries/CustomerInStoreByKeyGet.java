package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

public interface CustomerInStoreByKeyGet extends MetaModelGetDsl<Customer, Customer, CustomerInStoreByKeyGet, CustomerExpansionModel<Customer>> {

    static CustomerInStoreByKeyGet of(final String storeKey, final String id) {
        return new CustomerInStoreByKeyGetImpl(storeKey, id);
    }
    
}
