package io.sphere.sdk.customers.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.models.Versioned;

import java.util.List;
import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CustomerInStoreUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Customer, CustomerInStoreUpdateCommand, CustomerExpansionModel<Customer>> implements CustomerInStoreUpdateCommand {

    CustomerInStoreUpdateCommandImpl(final Versioned<Customer> versioned, final String storeKey, final List<? extends UpdateAction<Customer>> updateActions) {
        super(versioned, updateActions, JsonEndpoint.of(Customer.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/customers"), CustomerInStoreUpdateCommandImpl::new, CustomerExpansionModel.of());
    }

    CustomerInStoreUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Customer, CustomerInStoreUpdateCommand, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}
