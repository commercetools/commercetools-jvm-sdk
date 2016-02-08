package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets/unsets the middle name of a customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#nameUpdates()}
 *
 * @see Customer
 * @see io.sphere.sdk.customers.commands.CustomerUpdateCommand
 * @see Customer#getMiddleName() 
 */
public final class SetMiddleName extends UpdateActionImpl<Customer> {
    @Nullable
    private final String middleName;

    private SetMiddleName(@Nullable final String middleName) {
        super("setMiddleName");
        this.middleName = middleName;
    }

    public static SetMiddleName of(@Nullable final String middleName) {
        return new SetMiddleName(middleName);
    }

    @Nullable
    public String getMiddleName() {
        return middleName;
    }
}
