package io.sphere.sdk.customers.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.models.Versioned;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CustomerInStoreDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Customer, CustomerInStoreDeleteCommand, CustomerExpansionModel<Customer>> implements CustomerInStoreDeleteCommand {

    CustomerInStoreDeleteCommandImpl(final String storeKey, final Versioned<Customer> versioned, final boolean eraseData) {
        super(versioned,eraseData, JsonEndpoint.of(Customer.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/customers"), CustomerExpansionModel.of(), CustomerInStoreDeleteCommandImpl::new);
    }

    CustomerInStoreDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Customer, CustomerInStoreDeleteCommand, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}
