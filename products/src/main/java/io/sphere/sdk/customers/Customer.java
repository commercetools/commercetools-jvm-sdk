package io.sphere.sdk.customers;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import java.util.List;
import java.util.Optional;

public interface Customer extends DefaultModel<Customer> {
    Optional<String> getCustomerNumber();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getPassword();

    Optional<String> getMiddleName();

    Optional<String> getTitle();

    List<Address> getAddresses();

    Optional<String> getDefaultShippingAddressId();

    Optional<String> getDefaultBillingAddressId();

    boolean isEmailVerified();

    Optional<String> getExternalId();

    Optional<Reference<CustomerGroup>> getCustomerGroup();

    @Override
    default Reference toReference() {
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    public static String typeId(){
        return "customer";
    }
}
