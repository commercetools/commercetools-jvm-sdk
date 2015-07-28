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
public class CustomerGroupUpdateCommandImpl extends UpdateCommandDslImpl<CustomerGroup, CustomerGroupUpdateCommandImpl> {
    private CustomerGroupUpdateCommandImpl(final Versioned<CustomerGroup> versioned, final List<? extends UpdateAction<CustomerGroup>> updateActions) {
        super(versioned, updateActions, ENDPOINT);
    }

    public static CustomerGroupUpdateCommandImpl of(final Versioned<CustomerGroup> versioned, final List<? extends UpdateAction<CustomerGroup>> updateActions) {
        return new CustomerGroupUpdateCommandImpl(versioned, updateActions);
    }

    public static CustomerGroupUpdateCommandImpl of(final Versioned<CustomerGroup> versioned, final UpdateAction<CustomerGroup> updateAction) {
        return new CustomerGroupUpdateCommandImpl(versioned, asList(updateAction));
    }
}
