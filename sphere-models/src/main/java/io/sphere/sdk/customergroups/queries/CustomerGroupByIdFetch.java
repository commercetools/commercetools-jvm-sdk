package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface CustomerGroupByIdFetch extends MetaModelFetchDsl<CustomerGroup, CustomerGroup, CustomerGroupByIdFetch, CustomerGroupExpansionModel<CustomerGroup>> {
    static CustomerGroupByIdFetch of(final Identifiable<CustomerGroup> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CustomerGroupByIdFetch of(final String id) {
        return new CustomerGroupByIdFetchImpl(id);
    }

    @Override
    CustomerGroupByIdFetch plusExpansionPaths(final Function<CustomerGroupExpansionModel<CustomerGroup>, ExpansionPath<CustomerGroup>> m);

    @Override
    CustomerGroupByIdFetch withExpansionPaths(final Function<CustomerGroupExpansionModel<CustomerGroup>, ExpansionPath<CustomerGroup>> m);

    @Override
    List<ExpansionPath<CustomerGroup>> expansionPaths();

    @Override
    CustomerGroupByIdFetch plusExpansionPaths(final ExpansionPath<CustomerGroup> expansionPath);

    @Override
    CustomerGroupByIdFetch withExpansionPaths(final ExpansionPath<CustomerGroup> expansionPath);

    @Override
    CustomerGroupByIdFetch withExpansionPaths(final List<ExpansionPath<CustomerGroup>> expansionPaths);
}
