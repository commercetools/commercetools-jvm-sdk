package io.sphere.sdk.customers;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

/**
 * Parameter object for customer title, firstName, middleName and lastName.
 *
 * @see Customer
 * @see Customer#getName()
 * @see io.sphere.sdk.customers.commands.updateactions.SetFirstName
 * @see io.sphere.sdk.customers.commands.updateactions.SetMiddleName
 * @see io.sphere.sdk.customers.commands.updateactions.SetLastName
 * @see io.sphere.sdk.customers.commands.updateactions.SetTitle
 * @see CustomerDraftBuilder
 */
public final class CustomerName extends Base {
    @Nullable
    private final String title;
    @Nullable
    private final String firstName;
    @Nullable
    private final String middleName;
    @Nullable
    private final String lastName;

    private CustomerName(@Nullable final String title, @Nullable final String firstName, @Nullable final String middleName, @Nullable final String lastName) {
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getMiddleName() {
        return middleName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public CustomerName withTitle(@Nullable final String title) {
        return new CustomerName(title, firstName, middleName, lastName);
    }

    public CustomerName withMiddleName(@Nullable final String middleName) {
        return new CustomerName(title, firstName, middleName, lastName);
    }

    public static CustomerName ofFirstAndLastName(@Nullable final String firstName, @Nullable final String lastName) {
        return new CustomerName(null, firstName, null, lastName);
    }

    public static CustomerName ofTitleFirstAndLastName(@Nullable final String title, @Nullable final String firstName, @Nullable final String lastName) {
        return new CustomerName(title, firstName, null, lastName);
    }

    public static CustomerName of(@Nullable final String title, @Nullable final String firstName, @Nullable final String middleName, @Nullable final String lastName) {
        return new CustomerName(title, firstName, middleName, lastName);
    }
}
