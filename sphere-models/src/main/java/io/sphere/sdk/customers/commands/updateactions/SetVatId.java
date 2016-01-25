package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets a new vat ID for the customer
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setVatId()}
 *
 *  @see Customer
 */
public class SetVatId extends UpdateActionImpl<Customer> {
    @Nullable
    private final String vatId;

    private SetVatId(@Nullable final String vatId) {
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