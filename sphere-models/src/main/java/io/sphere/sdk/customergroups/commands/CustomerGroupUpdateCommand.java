package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static io.sphere.sdk.customergroups.commands.CustomerGroupEndpoint.ENDPOINT;
import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class CustomerGroupUpdateCommand extends UpdateCommandDslImpl<CustomerGroup, CustomerGroupUpdateCommand> {
    private CustomerGroupUpdateCommand(final Versioned<CustomerGroup> versioned, final List<? extends UpdateAction<CustomerGroup>> updateActions) {
        super(versioned, updateActions, ENDPOINT);
    }

    public static CustomerGroupUpdateCommand of(final Versioned<CustomerGroup> versioned, final List<? extends UpdateAction<CustomerGroup>> updateActions) {
        return new CustomerGroupUpdateCommand(versioned, updateActions);
    }

    public static CustomerGroupUpdateCommand of(final Versioned<CustomerGroup> versioned, final UpdateAction<CustomerGroup> updateAction) {
        return new CustomerGroupUpdateCommand(versioned, asList(updateAction));
    }
}
