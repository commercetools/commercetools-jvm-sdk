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
public class CustomerUpdateCommandImpl extends UpdateCommandDslImpl<Customer, CustomerUpdateCommandImpl> {
    private CustomerUpdateCommandImpl(final Versioned<Customer> versioned, final List<? extends UpdateAction<Customer>> updateActions) {
        super(versioned, updateActions, CustomerEndpoint.ENDPOINT);
    }

    public static CustomerUpdateCommandImpl of(final Versioned<Customer> versioned, final List<? extends UpdateAction<Customer>> updateActions) {
        return new CustomerUpdateCommandImpl(versioned, updateActions);
    }

    public static CustomerUpdateCommandImpl of(final Versioned<Customer> versioned, final UpdateAction<Customer> updateAction) {
        return of(versioned, asList(updateAction));
    }
}
