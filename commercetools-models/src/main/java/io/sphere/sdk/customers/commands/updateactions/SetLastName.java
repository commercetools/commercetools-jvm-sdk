package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets/unsets the last name of a customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#nameUpdates()}
 *
 * @see Customer
 * @see io.sphere.sdk.customers.commands.CustomerUpdateCommand
 * @see Customer#getLastName() 
 */
public final class SetLastName extends UpdateActionImpl<Customer> {
    @Nullable
    private final String lastName;

    private SetLastName(@Nullable final String lastName) {
        super("setLastName");
        this.lastName = lastName;
    }

    public static SetLastName of(@Nullable final String lastName) {
        return new SetLastName(lastName);
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }
}
