package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommandDsl;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.expansion.CustomerSignInResultExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

public interface CustomerInStoreCreateCommand extends DraftBasedCreateCommandDsl<CustomerSignInResult, CustomerDraft, CustomerInStoreCreateCommand>, MetaModelReferenceExpansionDsl<CustomerSignInResult, CustomerInStoreCreateCommand, CustomerSignInResultExpansionModel<CustomerSignInResult>> {
    static CustomerInStoreCreateCommand of(final String storeKey, final CustomerDraft draft) {
        return new CustomerInStoreCreateCommandImpl(storeKey, draft);
    }
}
