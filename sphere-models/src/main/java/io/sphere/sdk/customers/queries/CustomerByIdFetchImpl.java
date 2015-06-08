package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

public class CustomerByIdFetchImpl extends MetaModelFetchDslImpl<Customer, CustomerByIdFetch, CustomerExpansionModel<Customer>> implements CustomerByIdFetch {
    CustomerByIdFetchImpl(final String id) {
        super(id, CustomerEndpoint.ENDPOINT, CustomerExpansionModel.of(), CustomerByIdFetchImpl::new);
    }

    public CustomerByIdFetchImpl(MetaModelFetchDslBuilder<Customer, CustomerByIdFetch, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}
