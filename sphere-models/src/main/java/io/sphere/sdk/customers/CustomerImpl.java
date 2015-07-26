package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.SdkDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

class CustomerImpl extends DefaultModelImpl<Customer> implements Customer {
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
    private final boolean isEmailVerified;
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

    @JsonCreator
    public CustomerImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String customerNumber, final String email, final String firstName, final String lastName, final String password, final String middleName, final String title, final List<Address> addresses, final String defaultShippingAddressId, final String defaultBillingAddressId, final boolean isEmailVerified, final String externalId, final Reference<CustomerGroup> customerGroup, final String companyName,  final String vatId, final LocalDate dateOfBirth) {
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
    public boolean isEmailVerified() {
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

    //it is final to prevent subclasses to log the password
    @Override
    public final String toString() {
        final Customer out = new CustomerImpl(getId(), getVersion(), getCreatedAt(), getLastModifiedAt(),
                customerNumber, email, firstName, lastName, "**removed from output**", middleName,
                title, addresses, defaultShippingAddressId, defaultBillingAddressId,
                isEmailVerified, externalId, customerGroup, companyName, vatId, dateOfBirth);
        return ToStringBuilder.reflectionToString(out, SdkDefaults.TO_STRING_STYLE);
    }
}
