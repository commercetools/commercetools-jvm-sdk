package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerName;

import java.util.Optional;

/**
 * Changes customer's firstName, lastName, middleName and title fields.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#changeName()}
 */
public class ChangeName extends UpdateAction<Customer> {
    private final String firstName;
    private final String lastName;
    private final Optional<String> middleName;
    private final Optional<String> title;

    private ChangeName(final String firstName, final String lastName, final Optional<String> middleName, final Optional<String> title) {
        super("changeName");
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.title = title;
    }

    public static ChangeName of(final CustomerName name) {
        return new ChangeName(name.getFirstName(), name.getLastName(), name.getMiddleName(), name.getTitle());
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

    public Optional<String> getTitle() {
        return title;
    }
}
