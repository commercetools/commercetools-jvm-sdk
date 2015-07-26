package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public class CustomerGroupQueryModel extends DefaultModelQueryModelImpl<CustomerGroup> {
    private CustomerGroupQueryModel(final QueryModel<CustomerGroup> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    static CustomerGroupQueryModel of() {
        return new CustomerGroupQueryModel(null, null);
    }

    public StringQuerySortingModel<CustomerGroup> name() {
        return stringModel("name");
    }
}
