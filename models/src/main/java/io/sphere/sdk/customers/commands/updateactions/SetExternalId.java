package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;

import java.util.Optional;

/**
 * Sets a new ID which can be used as additional identifier for external Systems like CRM or ERP.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setExternalId()}
 */
public class SetExternalId extends UpdateAction<Customer> {
    private final Optional<String> externalId;

    private SetExternalId(final Optional<String> externalId) {
        super("setExternalId");
        this.externalId = externalId;
    }

    public static SetExternalId of(final Optional<String> externalId) {
        return new SetExternalId(externalId);
    }

    public static SetExternalId of(final String externalId) {
        return of(Optional.of(externalId));
    }

    public Optional<String> getExternalId() {
        return externalId;
    }
}
