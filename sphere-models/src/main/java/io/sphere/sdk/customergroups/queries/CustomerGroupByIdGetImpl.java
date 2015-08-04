package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class CustomerGroupByIdGetImpl extends MetaModelGetDslImpl<CustomerGroup, CustomerGroup, CustomerGroupByIdGet, CustomerGroupExpansionModel<CustomerGroup>> implements CustomerGroupByIdGet {
    CustomerGroupByIdGetImpl(final String id) {
        super(id, CustomerGroupEndpoint.ENDPOINT, CustomerGroupExpansionModel.of(), CustomerGroupByIdGetImpl::new);
    }

    public CustomerGroupByIdGetImpl(MetaModelGetDslBuilder<CustomerGroup, CustomerGroup, CustomerGroupByIdGet, CustomerGroupExpansionModel<CustomerGroup>> builder) {
        super(builder);
    }
}
