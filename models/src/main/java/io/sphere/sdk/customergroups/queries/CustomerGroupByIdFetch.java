package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.ByIdFetchImpl;

import static io.sphere.sdk.customergroups.queries.CustomerGroupEndpoint.ENDPOINT;

public class CustomerGroupByIdFetch extends ByIdFetchImpl<CustomerGroup> {
    private CustomerGroupByIdFetch(final String id) {
        super(id, ENDPOINT);
    }

    public static CustomerGroupByIdFetch of(final String id) {
        return new CustomerGroupByIdFetch(id);
    }
}
