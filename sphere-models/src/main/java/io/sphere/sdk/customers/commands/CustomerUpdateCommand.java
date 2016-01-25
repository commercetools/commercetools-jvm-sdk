package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 * Updates a customer.
 *
 {@doc.gen list actions}

 To update the properties {@link Customer#isEmailVerified()} or {@link Customer#getPassword()} special commands are required which are documented in the {@link Customer customer Javadoc}.

 @see Customer
 */
public interface CustomerUpdateCommand extends UpdateCommandDsl<Customer, CustomerUpdateCommand>, MetaModelReferenceExpansionDsl<Customer, CustomerUpdateCommand, CustomerExpansionModel<Customer>> {
    static CustomerUpdateCommand of(final Versioned<Customer> versioned, final List<? extends UpdateAction<Customer>> updateActions) {
        return new CustomerUpdateCommandImpl(versioned, updateActions);
    }

    static CustomerUpdateCommand of(final Versioned<Customer> versioned, final UpdateAction<Customer> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }
}
