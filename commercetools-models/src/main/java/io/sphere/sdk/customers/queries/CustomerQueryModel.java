package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public interface CustomerQueryModel extends ResourceQueryModel<Customer>, WithCustomQueryModel<Customer> {
    StringQuerySortingModel<Customer> firstName();

    StringQuerySortingModel<Customer> lastName();

    StringQuerySortingModel<Customer> email();

    StringQuerySortingModel<Customer> defaultShippingAddressId();

    StringQuerySortingModel<Customer> defaultBillingAddressId();

    BooleanQueryModel<Customer> isEmailVerified();

    StringQuerySortingModel<Customer> externalId();

    ReferenceQueryModel<Customer, CustomerGroup> customerGroup();

    @Override
    CustomQueryModel<Customer> custom();

    @Override
    QueryPredicate<Customer> is(Identifiable<Customer> identifiable);

    static CustomerQueryModel of() {
        return new CustomerQueryModelImpl(null, null);
    }
}
