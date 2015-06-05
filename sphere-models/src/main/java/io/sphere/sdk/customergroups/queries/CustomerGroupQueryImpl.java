package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;

/**
 {@doc.gen summary customer groups}
 */
final class CustomerGroupQueryImpl extends UltraQueryDslImpl<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel<CustomerGroup>, CustomerGroupExpansionModel<CustomerGroup>> implements CustomerGroupQuery {
    CustomerGroupQueryImpl(){
        super(CustomerGroupEndpoint.ENDPOINT.endpoint(), CustomerGroupQuery.resultTypeReference(), CustomerGroupQueryModel.of(), CustomerGroupExpansionModel.of(), CustomerGroupQueryImpl::new);
    }

    private CustomerGroupQueryImpl(final UltraQueryDslBuilder<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel<CustomerGroup>, CustomerGroupExpansionModel<CustomerGroup>> builder) {
        super(builder);
    }
}