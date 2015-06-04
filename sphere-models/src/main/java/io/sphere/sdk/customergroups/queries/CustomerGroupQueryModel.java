package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public class CustomerGroupQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    private CustomerGroupQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static CustomerGroupQueryModel<CustomerGroup> of() {
        return new CustomerGroupQueryModel<>(Optional.<QueryModel<CustomerGroup>>empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<T> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }
}
