package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

final class CustomerGroupQueryModelImpl extends ResourceQueryModelImpl<CustomerGroup> implements CustomerGroupQueryModel {
    CustomerGroupQueryModelImpl(final QueryModel<CustomerGroup> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<CustomerGroup> name() {
        return stringModel("name");
    }
}
