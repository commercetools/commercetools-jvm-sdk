package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface CustomerUpdateCommand extends UpdateCommandDsl<Customer, CustomerUpdateCommand> {
    static CustomerUpdateCommand of(final Versioned<Customer> versioned, final List<? extends UpdateAction<Customer>> updateActions) {
        return new CustomerUpdateCommandImpl(versioned, updateActions);
    }

    static CustomerUpdateCommand of(final Versioned<Customer> versioned, final UpdateAction<Customer> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }
}
