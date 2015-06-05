package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;

final class CustomerQueryImpl extends UltraQueryDslImpl<Customer, CustomerQuery, CustomerQueryModel<Customer>, CustomerExpansionModel<Customer>> implements CustomerQuery {
    CustomerQueryImpl(){
        super(CustomerEndpoint.ENDPOINT.endpoint(), CustomerQuery.resultTypeReference(), CustomerQueryModel.of(), CustomerExpansionModel.of(), CustomerQueryImpl::new);
    }

    private CustomerQueryImpl(final UltraQueryDslBuilder<Customer, CustomerQuery, CustomerQueryModel<Customer>, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}