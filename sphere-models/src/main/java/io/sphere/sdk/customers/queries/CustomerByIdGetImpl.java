package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class CustomerByIdGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerByIdGet, CustomerExpansionModel<Customer>> implements CustomerByIdGet {
    CustomerByIdGetImpl(final String id) {
        super(id, CustomerEndpoint.ENDPOINT, CustomerExpansionModel.of(), CustomerByIdGetImpl::new);
    }

    public CustomerByIdGetImpl(MetaModelFetchDslBuilder<Customer, Customer, CustomerByIdGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}
