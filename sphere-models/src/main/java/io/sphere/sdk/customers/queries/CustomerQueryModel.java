package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public final class CustomerQueryModel extends CustomResourceQueryModelImpl<Customer> implements WithCustomQueryModel<Customer> {

    public static CustomerQueryModel of() {
        return new CustomerQueryModel(null, null);
    }

    private CustomerQueryModel(final QueryModel<Customer> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<Customer> firstName() {
        return stringModel("firstName");
    }

    public StringQuerySortingModel<Customer> lastName() {
        return stringModel("lastName");
    }

    public StringQuerySortingModel<Customer> email() {
        return stringModel("email");
    }

    public StringQuerySortingModel<Customer> defaultShippingAddressId() {
        return stringModel("defaultShippingAddressId");
    }

    public StringQuerySortingModel<Customer> defaultBillingAddressId() {
        return stringModel("defaultBillingAddressId");
    }

    public BooleanQueryModel<Customer> isEmailVerified() {
        return booleanModel("isEmailVerified");
    }

    public StringQuerySortingModel<Customer> externalId() {
        return stringModel("externalId");
    }

    public ReferenceQueryModel<Customer, CustomerGroup> customerGroup() {
        return referenceModel("customerGroup");
    }

    @Override
    public CustomQueryModel<Customer> custom() {
        return super.custom();
    }

    @Override
    public QueryPredicate<Customer> is(final Identifiable<Customer> identifiable) {
        return super.is(identifiable);
    }
}
