package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets/unsets the title of a customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#nameUpdates()}
 *
 * @see Customer
 * @see io.sphere.sdk.customers.commands.CustomerUpdateCommand
 * @see Customer#getTitle()
 */
public class SetTitle extends UpdateActionImpl<Customer> {
    @Nullable
    private final String title;

    private SetTitle(@Nullable final String title) {
        super("setTitle");
        this.title = title;
    }

    public static SetTitle of(@Nullable final String title) {
        return new SetTitle(title);
    }

    @Nullable
    public String getTitle() {
        return title;
    }
}
