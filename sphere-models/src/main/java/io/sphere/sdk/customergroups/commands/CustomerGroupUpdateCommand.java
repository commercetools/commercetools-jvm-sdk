package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 * Updates a customer group.
 *
 {@doc.gen list actions}

 @see CustomerGroup
 */
public interface CustomerGroupUpdateCommand extends UpdateCommandDsl<CustomerGroup, CustomerGroupUpdateCommand>, MetaModelReferenceExpansionDsl<CustomerGroup, CustomerGroupUpdateCommand, CustomerGroupExpansionModel<CustomerGroup>> {
    static CustomerGroupUpdateCommand of(final Versioned<CustomerGroup> versioned, final List<? extends UpdateAction<CustomerGroup>> updateActions) {
        return new CustomerGroupUpdateCommandImpl(versioned, updateActions);
    }

    static CustomerGroupUpdateCommand of(final Versioned<CustomerGroup> versioned, final UpdateAction<CustomerGroup> updateAction) {
        return new CustomerGroupUpdateCommandImpl(versioned, Collections.singletonList(updateAction));
    }
}
