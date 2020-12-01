package io.sphere.sdk.customers.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.expansion.CustomerSignInResultExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CustomerInStoreCreateCommandImpl extends MetaModelCreateCommandImpl<CustomerSignInResult, CustomerInStoreCreateCommand, CustomerDraft, CustomerSignInResultExpansionModel<CustomerSignInResult>> implements CustomerInStoreCreateCommand {

    CustomerInStoreCreateCommandImpl(final MetaModelCreateCommandBuilder<CustomerSignInResult, CustomerInStoreCreateCommand, CustomerDraft, CustomerSignInResultExpansionModel<CustomerSignInResult>> builder) {
        super(builder);
    }

    CustomerInStoreCreateCommandImpl(final String storeKey, final CustomerDraft body) {
        super(body, JsonEndpoint.of(CustomerSignInResult.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/customers"), CustomerSignInResultExpansionModel.of(), CustomerInStoreCreateCommandImpl::new);
    }

    @Override
    public CustomerInStoreCreateCommand withDraft(final CustomerDraft draft) {
        return new CustomerInStoreCreateCommandImpl(copyBuilder().draft(draft));
    }
}
