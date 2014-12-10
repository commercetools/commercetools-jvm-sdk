package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.FetchByIdImpl;

import static io.sphere.sdk.customergroups.queries.CustomerGroupEndpoint.ENDPOINT;

public class CustomerGroupFetchById extends FetchByIdImpl<CustomerGroup> {
    private CustomerGroupFetchById(final String id) {
        super(id, ENDPOINT);
    }

    public static CustomerGroupFetchById of(final String id) {
        return new CustomerGroupFetchById(id);
    }
}
