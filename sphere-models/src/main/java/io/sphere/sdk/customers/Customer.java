package io.sphere.sdk.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@JsonDeserialize(as = CustomerImpl.class)
public interface Customer extends Resource<Customer> {
    @Nullable
    String getCustomerNumber();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getPassword();

    String getMiddleName();

    String getTitle();

    List<Address> getAddresses();

    @Nullable
    String getDefaultShippingAddressId();

    default Optional<Address> findDefaultShippingAddress() {
        return getAddresses().stream()
                .filter(address -> address.getId() != null && address.getId().equals(getDefaultShippingAddressId()))
                .findFirst();
    }

    @Nullable
    default Address getDefaultShippingAddress() {
        return findDefaultShippingAddress().orElse(null);
    }

    @Nullable
    String getDefaultBillingAddressId();

    @Nullable
    default Address getDefaultBillingAddress() {
        return findDefaultBillingAddress().orElse(null);
    }

    default Optional<Address> findDefaultBillingAddress() {
        final String defaultBillingAddressId = getDefaultBillingAddressId();

        return getAddresses().stream()
                .filter(address -> defaultBillingAddressId != null && address.getId() != null && address.getId().equals(defaultBillingAddressId))
                .findFirst();
    }

    Boolean isEmailVerified();

    @Nullable
    String getExternalId();

    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    default CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    @Nullable
    String getCompanyName();

    @Nullable
    String getVatId();

    @Nullable
    LocalDate getDateOfBirth();

    @Override
    default Reference<Customer> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static String resourceTypeId(){
        return "customer";
    }

    static String referenceTypeId(){
        return "customer";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     */
    @Deprecated
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
