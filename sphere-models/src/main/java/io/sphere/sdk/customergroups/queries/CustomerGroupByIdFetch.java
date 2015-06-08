package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface CustomerGroupByIdFetch extends MetaModelFetchDsl<CustomerGroup, CustomerGroupByIdFetch, CustomerGroupExpansionModel<CustomerGroup>> {
    static CustomerGroupByIdFetch of(final Identifiable<CustomerGroup> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CustomerGroupByIdFetch of(final String id) {
        return new CustomerGroupByIdFetchImpl(id);
    }
}
