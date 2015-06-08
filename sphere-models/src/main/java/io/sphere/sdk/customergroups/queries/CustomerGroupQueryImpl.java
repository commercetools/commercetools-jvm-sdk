package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

/**
 {@doc.gen summary customer groups}
 */
final class CustomerGroupQueryImpl extends MetaModelQueryDslImpl<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel, CustomerGroupExpansionModel<CustomerGroup>> implements CustomerGroupQuery {
    CustomerGroupQueryImpl(){
        super(CustomerGroupEndpoint.ENDPOINT.endpoint(), CustomerGroupQuery.resultTypeReference(), CustomerGroupQueryModel.of(), CustomerGroupExpansionModel.of(), CustomerGroupQueryImpl::new);
    }

    private CustomerGroupQueryImpl(final MetaModelQueryDslBuilder<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel, CustomerGroupExpansionModel<CustomerGroup>> builder) {
        super(builder);
    }
}