package io.sphere.sdk.customers;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomerDraftBuilder extends Base implements Builder<CustomerDraft> {
    private Optional<String> customerNumber = Optional.empty();
    private String email;
    private String firstName;
    private String lastName;
    private Optional<String> middleName = Optional.empty();
    private String password;
    private Optional<String> title = Optional.empty();
    private Optional<String> externalId = Optional.empty();
    private Optional<String> anonymousCartId = Optional.empty();
    private Optional<LocalDate> dateOfBirth = Optional.empty();
    private Optional<String> companyName = Optional.empty();
    private Optional<String> vatId = Optional.empty();
    private Optional<Boolean> emailVerified = Optional.empty();
    private Optional<Reference<CustomerGroup>> customerGroup = Optional.empty();
    private Optional<Integer> defaultBillingAddress = Optional.empty();
    private Optional<Integer> defaultShippingAddress = Optional.empty();
    private List<Address> addresses = Collections.emptyList();

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
                .emailVerified(template.IsEmailVerified())
                .customerGroup(template.getCustomerGroup())
                .addresses(template.getAddresses())
                .defaultBillingAddress(template.getDefaultBillingAddress())
                .defaultShippingAddress(template.getDefaultShippingAddress())
        ;
        return builder;
    }

    private CustomerDraftBuilder() {

    }

    public CustomerDraftBuilder customerNumber(final Optional<String> customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public CustomerDraftBuilder customerNumber(final String customerNumber) {
        return customerNumber(Optional.of(customerNumber));
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

    public CustomerDraftBuilder middleName(final Optional<String> middleName) {
        this.middleName = middleName;
        return this;
    }

    public CustomerDraftBuilder middleName(final String middleName) {
        return middleName(Optional.of(middleName));
    }

    public CustomerDraftBuilder password(final String password) {
        this.password = password;
        return this;
    }

    public CustomerDraftBuilder title(final Optional<String> title) {
        this.title = title;
        return this;
    }

    public CustomerDraftBuilder title(final String title) {
        return title(Optional.of(title));
    }

    public CustomerDraftBuilder externalId(final Optional<String> externalId) {
        this.externalId = externalId;
        return this;
    }

    public CustomerDraftBuilder externalId(final String externalId) {
        return externalId(Optional.of(externalId));
    }

    public CustomerDraftBuilder anonymousCartId(final Optional<String> anonymousCartId) {
        this.anonymousCartId = anonymousCartId;
        return this;
    }

    public CustomerDraftBuilder anonymousCartId(final String anonymousCartId) {
        return anonymousCartId(Optional.of(anonymousCartId));
    }
    
    public CustomerDraftBuilder dateOfBirth(final Optional<LocalDate> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public CustomerDraftBuilder dateOfBirth(final LocalDate dateOfBirth) {
        return dateOfBirth(Optional.of(dateOfBirth));
    }

    public CustomerDraftBuilder companyName(final Optional<String> companyName) {
        this.companyName = companyName;
        return this;
    }

    public CustomerDraftBuilder companyName(final String companyName) {
        return companyName(Optional.of(companyName));
    }
    
    public CustomerDraftBuilder vatId(final Optional<String> vatId) {
        this.vatId = vatId;
        return this;
    }

    public CustomerDraftBuilder vatId(final String vatId) {
        return vatId(Optional.of(vatId));
    }

    public CustomerDraftBuilder emailVerified(final Optional<Boolean> isEmailVerified) {
        return isEmailVerified(isEmailVerified);
    }

    public CustomerDraftBuilder emailVerified(final boolean isEmailVerified) {
        return isEmailVerified(isEmailVerified);
    }

    public CustomerDraftBuilder isEmailVerified(final Optional<Boolean> isEmailVerified) {
        this.emailVerified = isEmailVerified;
        return this;
    }

    public CustomerDraftBuilder isEmailVerified(final boolean isEmailVerified) {
        return isEmailVerified(Optional.of(isEmailVerified));
    }

    public CustomerDraftBuilder customerGroup(final Optional<Reference<CustomerGroup>> customerGroup) {
        this.customerGroup = customerGroup;
        return this;
    }

    public CustomerDraftBuilder customerGroup(final Referenceable<CustomerGroup> customerGroup) {
        return customerGroup(Optional.of(customerGroup.toReference()));
    }

    public CustomerDraftBuilder defaultBillingAddress(final Optional<Integer> defaultBillingAddress) {
        this.defaultBillingAddress = defaultBillingAddress;
        return this;
    }

    public CustomerDraftBuilder defaultBillingAddress(final int defaultBillingAddress) {
        return defaultBillingAddress(Optional.of(defaultBillingAddress));
    }

    public CustomerDraftBuilder defaultShippingAddress(final Optional<Integer> defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
        return this;
    }

    public CustomerDraftBuilder defaultShippingAddress(final int defaultShippingAddress) {
        return defaultShippingAddress(Optional.of(defaultShippingAddress));
    }

    public CustomerDraftBuilder addresses(final List<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    @Override
    public CustomerDraft build() {
        return new CustomerDraft(customerNumber, email, firstName, lastName, middleName, password, title, externalId, anonymousCartId, dateOfBirth, companyName, vatId, emailVerified, customerGroup, defaultBillingAddress, defaultShippingAddress, addresses);
    }

    //it is final to prevent subclasses to log the password
    @Override
    public final String toString() {
        final CustomerDraftBuilder out = CustomerDraftBuilder.of(build()).password("**removed from output**");
        return ToStringBuilder.reflectionToString(out, SdkDefaults.TO_STRING_STYLE);
    }
}
