package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class CustomerUpdateCommand extends UpdateCommandDslImpl<Customer> {
    private CustomerUpdateCommand(final Versioned<Customer> versioned, final List<? extends UpdateAction<Customer>> updateActions) {
        super(versioned, updateActions, CustomerEndpoint.ENDPOINT);
    }

    public static CustomerUpdateCommand of(final Versioned<Customer> versioned, final List<? extends UpdateAction<Customer>> updateActions) {
        return new CustomerUpdateCommand(versioned, updateActions);
    }

    public static CustomerUpdateCommand of(final Versioned<Customer> versioned, final UpdateAction<Customer> updateAction) {
        return of(versioned, asList(updateAction));
    }
}
