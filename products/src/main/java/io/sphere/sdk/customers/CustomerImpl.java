package io.sphere.sdk.customers;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

class CustomerImpl extends DefaultModelImpl<Customer> implements Customer {

    private final Optional<String> customerNumber;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final Optional<String> middleName;
    private final Optional<String> title;
    private final List<Address> addresses;
    private final Optional<String> defaultShippingAddressId;
    private final Optional<String> defaultBillingAddressId;
    private final boolean isEmailVerified;
    private final Optional<String> externalId;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<String> companyName;
    private final Optional<String> vatId;
    private final Optional<LocalDate> dateOfBirth;

    public CustomerImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt, final Optional<String> customerNumber, final String email, final String firstName, final String lastName, final String password, final Optional<String> middleName, final Optional<String> title, final List<Address> addresses, final Optional<String> defaultShippingAddressId, final Optional<String> defaultBillingAddressId, final boolean isEmailVerified, final Optional<String> externalId, final Optional<Reference<CustomerGroup>> customerGroup, final Optional<String> companyName,  final Optional<String> vatId, final Optional<LocalDate> dateOfBirth) {
        super(id, version, createdAt, lastModifiedAt);
        this.customerNumber = customerNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.middleName = middleName;
        this.title = title;
        this.addresses = addresses;
        this.defaultShippingAddressId = defaultShippingAddressId;
        this.defaultBillingAddressId = defaultBillingAddressId;
        this.isEmailVerified = isEmailVerified;
        this.externalId = externalId;
        this.customerGroup = customerGroup;
        this.companyName = companyName;
        this.vatId = vatId;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Optional<String> getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Optional<String> getMiddleName() {
        return middleName;
    }

    @Override
    public Optional<String> getTitle() {
        return title;
    }

    @Override
    public List<Address> getAddresses() {
        return addresses;
    }

    @Override
    public Optional<String> getDefaultShippingAddressId() {
        return defaultShippingAddressId;
    }

    @Override
    public Optional<String> getDefaultBillingAddressId() {
        return defaultBillingAddressId;
    }

    @Override
    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    @Override
    public Optional<String> getExternalId() {
        return externalId;
    }

    @Override
    public Optional<Reference<CustomerGroup>> getCustomerGroup() {
        return customerGroup;
    }

    @Override
    public Optional<String> getCompanyName() {
        return companyName;
    }

    @Override
    public Optional<String> getVatId() {
        return vatId;
    }

    @Override
    public Optional<LocalDate> getDateOfBirth() {
        return dateOfBirth;
    }
}
