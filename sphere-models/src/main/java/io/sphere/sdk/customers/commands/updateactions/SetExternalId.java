package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets a new ID which can be used as additional identifier for external Systems like CRM or ERP.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setExternalId()}
 */
public class SetExternalId extends UpdateAction<Customer> {
    @Nullable
    private final String externalId;

    private SetExternalId(@Nullable final String externalId) {
        super("setExternalId");
        this.externalId = externalId;
    }

    public static SetExternalId of(@Nullable final String externalId) {
        return new SetExternalId(externalId);
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }
}
