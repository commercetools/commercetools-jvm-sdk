package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.models.Versioned;

final class CustomerDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Customer, CustomerDeleteCommand, CustomerExpansionModel<Customer>> implements CustomerDeleteCommand {
    CustomerDeleteCommandImpl(final Versioned<Customer> versioned) {
        super(versioned, CustomerEndpoint.ENDPOINT, CustomerExpansionModel.of(), CustomerDeleteCommandImpl::new);
    }

    CustomerDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Customer, CustomerDeleteCommand, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}
