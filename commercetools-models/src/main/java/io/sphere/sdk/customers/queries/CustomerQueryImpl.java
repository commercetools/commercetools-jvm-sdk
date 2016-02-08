package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

final class CustomerQueryImpl extends MetaModelQueryDslImpl<Customer, CustomerQuery, CustomerQueryModel, CustomerExpansionModel<Customer>> implements CustomerQuery {
    CustomerQueryImpl(){
        super(CustomerEndpoint.ENDPOINT.endpoint(), CustomerQuery.resultTypeReference(), CustomerQueryModel.of(), CustomerExpansionModel.of(), CustomerQueryImpl::new);
    }

    private CustomerQueryImpl(final MetaModelQueryDslBuilder<Customer, CustomerQuery, CustomerQueryModel, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}