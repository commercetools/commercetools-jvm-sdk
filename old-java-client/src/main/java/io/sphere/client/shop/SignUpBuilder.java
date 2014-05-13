package io.sphere.client.shop;

import com.google.common.base.Optional;
import io.sphere.client.shop.model.CustomerName;
import io.sphere.internal.command.CustomerCommands;
import net.jcip.annotations.NotThreadSafe;

/**
 * Builder to create data for a signup.
 */
@NotThreadSafe
public class SignUpBuilder {
    private final String email;
    private final String password;
    private final CustomerName customerName;
    private Optional<String> anonymousCartId = Optional.absent();
    private Optional<String> customerNumber = Optional.absent();
    private Optional<String> externalId = Optional.absent();

    public SignUpBuilder(String email, String password, CustomerName customerName) {
        this.email = email;
        this.password = password;
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public CustomerName getCustomerName() {
        return customerName;
    }

    public Optional<String> getAnonymousCartId() {
        return anonymousCartId;
    }

    public Optional<String> getCustomerNumber() {
        return customerNumber;
    }

    public Optional<String> getExternalId() {
        return externalId;
    }

    public SignUpBuilder setAnonymousCartId(final String anonymousCartId) {
        this.anonymousCartId = Optional.fromNullable(anonymousCartId);
        return this;
    }

    public SignUpBuilder setCustomerNumber(final String customerNumber) {
        this.customerNumber = Optional.fromNullable(customerNumber);
        return this;
    }

    public SignUpBuilder setExternalId(final String externalId) {
        this.externalId = Optional.fromNullable(externalId);
        return this;
    }

    public CustomerCommands.CreateCustomer build() {
        return new CustomerCommands.CreateCustomer(email, password, customerName.getFirstName(),
                customerName.getLastName(), customerName.getMiddleName(), customerName.getTitle(), anonymousCartId,
                customerNumber, externalId);
    }
}
