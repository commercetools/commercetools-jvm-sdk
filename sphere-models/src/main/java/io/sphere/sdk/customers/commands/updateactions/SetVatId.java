package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets a new vat ID for the customer
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setVatId()}
 */
public class SetVatId extends UpdateAction<Customer> {
    @Nullable
    private final String vatId;

    private SetVatId(final String vatId) {
        super("setVatId");
        this.vatId = vatId;
    }

    public static SetVatId of(@Nullable final String vatId) {
        return new SetVatId(vatId);
    }

    @Nullable
    public String getVatId() {
        return vatId;
    }
}