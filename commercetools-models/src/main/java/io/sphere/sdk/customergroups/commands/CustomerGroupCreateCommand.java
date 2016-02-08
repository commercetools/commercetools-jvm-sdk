package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupDraft;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 * <p>Creates a new customer group</p>
 *
 * {@include.example io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommandTest#execution()}
 *
 * @see CustomerGroup
 */
public interface CustomerGroupCreateCommand extends DraftBasedCreateCommand<CustomerGroup, CustomerGroupDraft>, MetaModelReferenceExpansionDsl<CustomerGroup, CustomerGroupCreateCommand, CustomerGroupExpansionModel<CustomerGroup>> {
    static CustomerGroupCreateCommand of(final CustomerGroupDraft draft) {
        return new CustomerGroupCreateCommandImpl(draft);
    }

    static CustomerGroupCreateCommand of(final String groupName) {
        return of(CustomerGroupDraft.of(groupName));
    }
}
