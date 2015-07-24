package io.sphere.sdk.customers;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public class CustomerName extends Base {
    @Nullable
    private final String title;
    private final String firstName;
    @Nullable
    private final String middleName;
    private final String lastName;

    private CustomerName(final String title, final String firstName, final String middleName, final String lastName) {
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public static CustomerName ofFirstAndLastName(final String firstName, final String lastName) {
        return new CustomerName(null, firstName, null, lastName);
    }

    public static CustomerName ofTitleFirstAndLastName(final String title, final String firstName, final String lastName) {
        return new CustomerName(title, firstName, null, lastName);
    }

    public static CustomerName of(@Nullable final String title, final String firstName, @Nullable final String middleName, final String lastName) {
        return new CustomerName(title, firstName, middleName, lastName);
    }
}
