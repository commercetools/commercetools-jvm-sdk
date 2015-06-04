package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public final class CustomerQueryModel<T> extends DefaultModelQueryModelImpl<T> {

    public static CustomerQueryModel<Customer> of() {
        return new CustomerQueryModel<>(Optional.<QueryModel<Customer>>empty(), Optional.<String>empty());
    }

    private CustomerQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> firstName() {
        return new StringQuerySortingModel<>(Optional.of(this), "firstName");
    }

    public StringQuerySortingModel<T> lastName() {
        return new StringQuerySortingModel<>(Optional.of(this), "lastName");
    }

    public StringQuerySortingModel<T> email() {
        return new StringQuerySortingModel<>(Optional.of(this), "email");
    }

    public StringQuerySortingModel<T> defaultShippingAddressId() {
        return new StringQuerySortingModel<>(Optional.of(this), "defaultBillingAddressId");
    }

    public StringQuerySortingModel<T> defaultBillingAddressId() {
        return new StringQuerySortingModel<>(Optional.of(this), "defaultBillingAddressId");
    }

    public BooleanQueryModel<T> isEmailVerified() {
        return new BooleanQueryModel<>(Optional.of(this), "isEmailVerified");
    }


    public StringQuerySortingModel<T> externalId() {
        return new StringQuerySortingModel<>(Optional.of(this), "externalId");
    }

    public ReferenceQueryModel<T, CustomerGroup> customerGroup() {
        return new ReferenceQueryModel<>(Optional.of(this), "customerGroup");
    }
}
