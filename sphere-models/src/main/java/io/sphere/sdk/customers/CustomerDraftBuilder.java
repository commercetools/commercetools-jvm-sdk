package io.sphere.sdk.customers;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.SdkDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
                .anonymousCartId(template.getAnonymousCartId());
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

    @Override
    public CustomerDraft build() {
        return new CustomerDraft(customerNumber, email, firstName, lastName, middleName, password, title, externalId, anonymousCartId);
    }

    //it is final to prevent subclasses to log the password
    @Override
    public final String toString() {
        final CustomerDraftBuilder out = CustomerDraftBuilder.of(build()).password("**removed from output**");
        return ToStringBuilder.reflectionToString(out, SdkDefaults.TO_STRING_STYLE);
    }
}
