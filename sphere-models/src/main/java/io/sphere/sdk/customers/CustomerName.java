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

    @Nullable
    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomerName withTitle(@Nullable final String title) {
        return new CustomerName(title, firstName, middleName, lastName);
    }

    public CustomerName withMiddleName(@Nullable final String middleName) {
        return new CustomerName(title, firstName, middleName, lastName);
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
