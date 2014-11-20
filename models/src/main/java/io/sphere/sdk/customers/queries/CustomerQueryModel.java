package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public final class CustomerQueryModel extends DefaultModelQueryModelImpl<Customer> {

    static CustomerQueryModel get() {
        return new CustomerQueryModel(Optional.<QueryModel<Customer>>empty(), Optional.<String>empty());
    }

    private CustomerQueryModel(final Optional<? extends QueryModel<Customer>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<Customer> firstName() {
        return new StringQuerySortingModel<>(Optional.of(this), "firstName");
    }

    public StringQuerySortingModel<Customer> lastName() {
        return new StringQuerySortingModel<>(Optional.of(this), "lastName");
    }

    public StringQuerySortingModel<Customer> email() {
        return new StringQuerySortingModel<>(Optional.of(this), "email");
    }

    public StringQuerySortingModel<Customer> defaultShippingAddressId() {
        return new StringQuerySortingModel<>(Optional.of(this), "defaultBillingAddressId");
    }

    public StringQuerySortingModel<Customer> defaultBillingAddressId() {
        return new StringQuerySortingModel<>(Optional.of(this), "defaultBillingAddressId");
    }

    public BooleanQueryModel<Customer> isEmailVerified() {
        return new BooleanQueryModel<>(Optional.of(this), "isEmailVerified");
    }


    public StringQuerySortingModel<Customer> externalId() {
        return new StringQuerySortingModel<>(Optional.of(this), "externalId");
    }
}
