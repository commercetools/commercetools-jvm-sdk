package io.sphere.sdk.customers;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.*;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Builder for creating a customer.
 *
 * @see Customer
 * @see CustomerDraftDsl
 * @see io.sphere.sdk.customers.commands.CustomerCreateCommand
 */
public class CustomerDraftBuilder extends Base implements Builder<CustomerDraftDsl> {
    @Nullable
    private String customerNumber;
    private String email;
    private String firstName;
    private String lastName;
    @Nullable
    private String middleName;
    private String password;
    @Nullable
    private String title;
    @Nullable
    private String externalId;
    @Nullable
    private String anonymousCartId;
    @Nullable
    private LocalDate dateOfBirth;
    @Nullable
    private String companyName;
    @Nullable
    private String vatId;
    @Nullable
    private Boolean emailVerified;
    @Nullable
    private Reference<CustomerGroup> customerGroup;
    @Nullable
    private Integer defaultBillingAddress;
    @Nullable
    private Integer defaultShippingAddress;
    private List<Address> addresses = Collections.emptyList();
    @Nullable
    private CustomFieldsDraft custom;


    public static CustomerDraftBuilder of(final String email, final String password) {
        return of(CustomerName.of(null, null, null, null), email, password);
    }

    public static CustomerDraftBuilder of(final CustomerName customerName, final String email, final String password) {
        final CustomerDraftBuilder customerDraftBuilder = new CustomerDraftBuilder();
        customerDraftBuilder.email = email;
        customerDraftBuilder.password = password;
        customerDraftBuilder.firstName = customerName.getFirstName();
        customerDraftBuilder.lastName = customerName.getLastName();
        customerDraftBuilder.middleName = customerName.getMiddleName();
        customerDraftBuilder.title = customerName.getTitle();
        return customerDraftBuilder;
    }

    public static CustomerDraftBuilder of(final CustomerDraft template) {
        final CustomerDraftBuilder builder = CustomerDraftBuilder.of(template.getName(), template.getEmail(), template.getPassword());
        builder.customerNumber(template.getCustomerNumber())
                .password(template.getPassword())
                .externalId(template.getExternalId())
                .anonymousCartId(template.getAnonymousCartId())
                .dateOfBirth(template.getDateOfBirth())
                .companyName(template.getCompanyName())
                .vatId(template.getVatId())
                .emailVerified(template.isEmailVerified())
                .customerGroup(template.getCustomerGroup())
                .addresses(template.getAddresses())
                .defaultBillingAddress(template.getDefaultBillingAddress())
                .defaultShippingAddress(template.getDefaultShippingAddress())
                .custom(template.getCustom())
        ;
        return builder;
    }

    private CustomerDraftBuilder() {

    }

    public CustomerDraftBuilder customerNumber(@Nullable final String customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public CustomerDraftBuilder email(final String email) {
        this.email = email;
        return this;
    }

    public CustomerDraftBuilder firstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerDraftBuilder lastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerDraftBuilder middleName(@Nullable final String middleName) {
        this.middleName = middleName;
        return this;
    }

    public CustomerDraftBuilder password(final String password) {
        this.password = password;
        return this;
    }

    public CustomerDraftBuilder title(@Nullable final String title) {
        this.title = title;
        return this;
    }

    public CustomerDraftBuilder externalId(@Nullable final String externalId) {
        this.externalId = externalId;
        return this;
    }

    public CustomerDraftBuilder anonymousCartId(@Nullable final String anonymousCartId) {
        this.anonymousCartId = anonymousCartId;
        return this;
    }

    public CustomerDraftBuilder dateOfBirth(@Nullable final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public CustomerDraftBuilder companyName(@Nullable final String companyName) {
        this.companyName = companyName;
        return this;
    }
    
    public CustomerDraftBuilder vatId(@Nullable final String vatId) {
        this.vatId = vatId;
        return this;
    }

    public CustomerDraftBuilder emailVerified(@Nullable final Boolean isEmailVerified) {
        return isEmailVerified(isEmailVerified);
    }

    public CustomerDraftBuilder isEmailVerified(@Nullable final Boolean isEmailVerified) {
        this.emailVerified = isEmailVerified;
        return this;
    }

    public CustomerDraftBuilder customerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        this.customerGroup =  Optional.ofNullable(customerGroup).map(Referenceable::toReference).orElse(null);
        return this;
    }

    public CustomerDraftBuilder defaultBillingAddress(@Nullable final Integer defaultBillingAddress) {
        this.defaultBillingAddress = defaultBillingAddress;
        return this;
    }

    public CustomerDraftBuilder defaultShippingAddress(@Nullable final Integer defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
        return this;
    }

    public CustomerDraftBuilder addresses(final List<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public CustomerDraftBuilder custom(@Nullable final CustomFieldsDraft custom) {
        this.custom = custom;
        return this;
    }

    @Override
    public CustomerDraftDsl build() {
        return new CustomerDraftDsl(customerNumber, email, firstName, lastName, middleName, password, title, externalId, anonymousCartId, dateOfBirth, companyName, vatId, emailVerified, customerGroup, defaultBillingAddress, defaultShippingAddress, addresses, custom);
    }
}
