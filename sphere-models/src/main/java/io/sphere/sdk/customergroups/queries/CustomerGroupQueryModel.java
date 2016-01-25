package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public interface CustomerGroupQueryModel extends ResourceQueryModel<CustomerGroup> {
    StringQuerySortingModel<CustomerGroup> name();

    static CustomerGroupQueryModel of() {
        return new CustomerGroupQueryModelImpl(null, null);
    }
}
