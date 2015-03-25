package io.sphere.sdk.customers;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.SdkDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Optional;

/**
 * Template to create a new Customer.
 *
 * @see io.sphere.sdk.customers.CustomerDraftBuilder
 * @see io.sphere.sdk.customers.commands.CustomerCreateCommand
 */
public class CustomerDraft extends Base {
    private final Optional<String> customerNumber;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Optional<String> middleName;
    private final String password;
    private final Optional<String> title;
    private final Optional<String> externalId;
    private final Optional<String> anonymousCartId;

    CustomerDraft(final Optional<String> customerNumber, final String email,
                  final String firstName, final String lastName, final Optional<String> middleName,
                  final String password, final Optional<String> title,
                  final Optional<String> externalId,
                  final Optional<String> anonymousCartId) {
        this.customerNumber = customerNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
        this.title = title;
        this.externalId = externalId;
        this.anonymousCartId = anonymousCartId;
    }

    public static CustomerDraft of(final CustomerName customerName, final String email, final String password) {
        return CustomerDraftBuilder.of(customerName, email, password).build();
    }

    public Optional<String> getCustomerNumber() {
        return customerNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Optional<String> getMiddleName() {
        return middleName;
    }

    public String getPassword() {
        return password;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getExternalId() {
        return externalId;
    }

    public Optional<String> getAnonymousCartId() {
        return anonymousCartId;
    }

    public CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    public CustomerDraft withCustomerNumber(final Optional<String> customerNumber) {
        return newBuilder().customerNumber(customerNumber).build();
    }

    public CustomerDraft withCustomerNumber(final String customerNumber) {
        return withCustomerNumber(Optional.of(customerNumber));
    }
    
    public CustomerDraft withExternalId(final Optional<String> externalId) {
        return newBuilder().externalId(externalId).build();
    }

    public CustomerDraft withExternalId(final String externalId) {
        return withExternalId(Optional.of(externalId));
    }    
    
    public CustomerDraft withAnonymousCartId(final Optional<String> anonymousCartId) {
        return newBuilder().anonymousCartId(anonymousCartId).build();
    }

    public CustomerDraft withPassword(final String password) {
        return newBuilder().password(password).build();
    }

    public CustomerDraft withCart(final Cart cart) {
        return withAnonymousCartId(Optional.of(cart.getId()));
    }

    private CustomerDraftBuilder newBuilder() {
        return CustomerDraftBuilder.of(this);
    }

    //it is final to prevent subclasses to log the password
    @Override
    public final String toString() {
        final CustomerDraft outObject = withPassword("**removed from output**");
        return ToStringBuilder.reflectionToString(outObject, SdkDefaults.TO_STRING_STYLE);
    }
}
