package io.sphere.sdk.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@JsonDeserialize(as = CustomerImpl.class)
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

    default Optional<Address> getDefaultShippingAddress() {
        return getAddresses().stream()
                .filter(address -> address.getId().isPresent() && address.getId().equals(getDefaultShippingAddressId()))
                .findFirst();
    }

    Optional<String> getDefaultBillingAddressId();

    default Optional<Address> getDefaultBillingAddress() {
        return getAddresses().stream()
                .filter(address -> address.getId().isPresent() && address.getId().equals(getDefaultBillingAddressId()))
                .findFirst();
    }

    boolean isEmailVerified();

    Optional<String> getExternalId();

    Optional<Reference<CustomerGroup>> getCustomerGroup();

    default CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    Optional<String> getCompanyName();

    Optional<String> getVatId();

    Optional<LocalDate> getDateOfBirth();

    @Override
    default Reference<Customer> toReference() {
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    public static String typeId(){
        return "customer";
    }

    public static TypeReference<Customer> typeReference(){
        return new TypeReference<Customer>() {
            @Override
            public String toString() {
                return "TypeReference<Customer>";
            }
        };
    }
}
