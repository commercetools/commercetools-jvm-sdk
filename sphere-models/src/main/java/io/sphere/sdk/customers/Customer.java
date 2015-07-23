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
                .filter(address -> address.getId() != null && Optional.of(address.getId()).equals(getDefaultShippingAddressId()))
                .findFirst();
    }

    Optional<String> getDefaultBillingAddressId();

    default Optional<Address> getDefaultBillingAddress() {
        return getAddresses().stream()
                .filter(address -> {
                    final Optional<String> defaultBillingAddressId = getDefaultBillingAddressId();
                    return address.getId() != null && Optional.of(address.getId()).equals(defaultBillingAddressId);
                })
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
        return Reference.of(typeId(), getId(), this);
    }

    static String typeId(){
        return "customer";
    }

    static TypeReference<Customer> typeReference(){
        return new TypeReference<Customer>() {
            @Override
            public String toString() {
                return "TypeReference<Customer>";
            }
        };
    }
}
