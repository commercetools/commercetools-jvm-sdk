package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public class CustomerGroupQueryModel extends QueryModelImpl<CustomerGroup> {
    private CustomerGroupQueryModel(final Optional<? extends QueryModel<CustomerGroup>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static CustomerGroupQueryModel get() {
        return new CustomerGroupQueryModel(Optional.<QueryModel<CustomerGroup>>empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<CustomerGroup> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }
}
