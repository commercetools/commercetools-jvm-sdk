package io.sphere.sdk.customers.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class CustomerInStoreByIdGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerInStoreByIdGet, CustomerExpansionModel<Customer>> implements CustomerInStoreByIdGet {

    CustomerInStoreByIdGetImpl(final String storeKey, final String id) {
        super(id, JsonEndpoint.of(Customer.typeReference(), "/in-store/key=" + storeKey + "/customers"), CustomerExpansionModel.of(), CustomerInStoreByIdGetImpl::new);
    }

    public CustomerInStoreByIdGetImpl(final MetaModelGetDslBuilder<Customer, Customer, CustomerInStoreByIdGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}