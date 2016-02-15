package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a customer group.
 *
 * {@include.example io.sphere.sdk.customergroups.commands.CustomerGroupDeleteCommandIntegrationTest#execution()}
 *
 * @see CustomerGroup
 */
public interface CustomerGroupDeleteCommand extends MetaModelReferenceExpansionDsl<CustomerGroup, CustomerGroupDeleteCommand, CustomerGroupExpansionModel<CustomerGroup>>, DeleteCommand<CustomerGroup> {
    static DeleteCommand<CustomerGroup> of(final Versioned<CustomerGroup> customerGroup) {
        return new CustomerGroupDeleteCommandImpl(customerGroup);
    }
}
