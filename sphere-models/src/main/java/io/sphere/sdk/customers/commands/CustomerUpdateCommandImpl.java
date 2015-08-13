package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.models.Versioned;

import java.util.List;


final class CustomerUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Customer, CustomerUpdateCommand, CustomerExpansionModel<Customer>> implements CustomerUpdateCommand {
    CustomerUpdateCommandImpl(final Versioned<Customer> versioned, final List<? extends UpdateAction<Customer>> updateActions) {
        super(versioned, updateActions, CustomerEndpoint.ENDPOINT, CustomerUpdateCommandImpl::new, CustomerExpansionModel.of());
    }

    CustomerUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Customer, CustomerUpdateCommand, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}
