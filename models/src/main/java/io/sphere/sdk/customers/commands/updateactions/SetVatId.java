package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;

import java.util.Optional;

/**
 * Sets a new vat ID for the customer
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setVatId()}
 */
public class SetVatId extends UpdateAction<Customer> {
    private final Optional<String> vatId;

    private SetVatId(final Optional<String> vatId) {
        super("setVatId");
        this.vatId = vatId;
    }

    public static SetVatId of(final Optional<String> vatId) {
        return new SetVatId(vatId);
    }

    public static SetVatId of(final String vatId) {
        return of(Optional.of(vatId));
    }

    public Optional<String> getVatId() {
        return vatId;
    }
}