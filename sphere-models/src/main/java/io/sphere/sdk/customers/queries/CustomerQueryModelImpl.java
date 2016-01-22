package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;

final class CustomerQueryModelImpl extends CustomResourceQueryModelImpl<Customer> implements CustomerQueryModel {

    CustomerQueryModelImpl(final QueryModel<Customer> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<Customer> firstName() {
        return stringModel("firstName");
    }

    @Override
    public StringQuerySortingModel<Customer> lastName() {
        return stringModel("lastName");
    }

    @Override
    public StringQuerySortingModel<Customer> email() {
        return stringModel("email");
    }

    @Override
    public StringQuerySortingModel<Customer> defaultShippingAddressId() {
        return stringModel("defaultShippingAddressId");
    }

    @Override
    public StringQuerySortingModel<Customer> defaultBillingAddressId() {
        return stringModel("defaultBillingAddressId");
    }

    @Override
    public BooleanQueryModel<Customer> isEmailVerified() {
        return booleanModel("isEmailVerified");
    }

    @Override
    public StringQuerySortingModel<Customer> externalId() {
        return stringModel("externalId");
    }

    @Override
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
