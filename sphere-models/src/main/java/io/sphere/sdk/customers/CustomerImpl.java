package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.SdkDefaults;
import io.sphere.sdk.types.CustomFields;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

class CustomerImpl extends ResourceImpl<Customer> implements Customer {
    @Nullable
    private final String customerNumber;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
    @Nullable
    private final String middleName;
    @Nullable
    private final String title;
    private final List<Address> addresses;
    @Nullable
    private final String defaultShippingAddressId;
    @Nullable
    private final String defaultBillingAddressId;
    private final Boolean isEmailVerified;
    @Nullable
    private final String externalId;
    @Nullable
    private final Reference<CustomerGroup> customerGroup;
    @Nullable
    private final String companyName;
    @Nullable
    private final String vatId;
    @Nullable
    private final LocalDate dateOfBirth;
    @Nullable
    private final CustomFields custom;

    @JsonCreator
    public CustomerImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, @Nullable final String customerNumber, final String email, final String firstName, final String lastName, final String password, @Nullable final String middleName, @Nullable final String title, final List<Address> addresses, @Nullable final String defaultShippingAddressId, @Nullable final String defaultBillingAddressId, final Boolean isEmailVerified, @Nullable final String externalId, @Nullable final Reference<CustomerGroup> customerGroup, @Nullable final String companyName, @Nullable final String vatId, @Nullable final LocalDate dateOfBirth, @Nullable final CustomFields custom) {
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
        this.custom = custom;
    }

    @Nullable
    @Override
    public String getCustomerNumber() {
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
    @Nullable
    public String getMiddleName() {
        return middleName;
    }

    @Override
    @Nullable
    public String getTitle() {
        return title;
    }

    @Override
    public List<Address> getAddresses() {
        return addresses;
    }

    @Override
    @Nullable
    public String getDefaultShippingAddressId() {
        return defaultShippingAddressId;
    }

    @Override
    @Nullable
    public String getDefaultBillingAddressId() {
        return defaultBillingAddressId;
    }

    @Override
    public Boolean isEmailVerified() {
        return isEmailVerified;
    }

    @Override
    @Nullable
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Override
    @Nullable
    public String getCompanyName() {
        return companyName;
    }

    @Override
    @Nullable
    public String getVatId() {
        return vatId;
    }

    @Override
    @Nullable
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }
}
