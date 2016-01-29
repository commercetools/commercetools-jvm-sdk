package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets/unsets the first name of a customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#nameUpdates()}
 *
 * @see Customer
 * @see io.sphere.sdk.customers.commands.CustomerUpdateCommand
 * @see Customer#getFirstName() 
 */
public class SetFirstName extends UpdateActionImpl<Customer> {
    @Nullable
    private final String firstName;

    private SetFirstName(@Nullable final String firstName) {
        super("setFirstName");
        this.firstName = firstName;
    }

    public static SetFirstName of(@Nullable final String firstName) {
        return new SetFirstName(firstName);
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }
}
