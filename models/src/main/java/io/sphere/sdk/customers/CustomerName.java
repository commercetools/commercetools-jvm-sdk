package io.sphere.sdk.customers;

import io.sphere.sdk.models.Base;

import java.util.Optional;

public class CustomerName extends Base {
    private final Optional<String> title;
    private final String firstName;
    private final Optional<String> middleName;
    private final String lastName;

    private CustomerName(final Optional<String> title, final String firstName, final Optional<String> middleName, final String lastName) {
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public Optional<String> getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public static CustomerName ofFirstAndLastName(final String firstName, final String lastName) {
        return new CustomerName(Optional.empty(), firstName, Optional.empty(), lastName);
    }

    public static CustomerName ofTitleFirstAndLastName(final String title, final String firstName, final String lastName) {
        return new CustomerName(Optional.of(title), firstName, Optional.empty(), lastName);
    }

    public static CustomerName of(final Optional<String> title, final String firstName, final Optional<String> middleName, final String lastName) {
        return new CustomerName(title, firstName, middleName, lastName);
    }
}
