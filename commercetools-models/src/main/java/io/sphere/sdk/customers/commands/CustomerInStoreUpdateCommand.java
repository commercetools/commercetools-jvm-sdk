package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public interface CustomerInStoreUpdateCommand extends UpdateCommandDsl<Customer, CustomerInStoreUpdateCommand>, MetaModelReferenceExpansionDsl<Customer, CustomerInStoreUpdateCommand, CustomerExpansionModel<Customer>> {
    
    static CustomerInStoreUpdateCommand of(final Versioned<Customer> versioned, final String storeKey, final List<? extends UpdateAction<Customer>> updateActions) {
        return new CustomerInStoreUpdateCommandImpl(versioned, storeKey, updateActions);
    }

    @SafeVarargs
    static CustomerInStoreUpdateCommand of(final Versioned<Customer> versioned, final String storeKey, final UpdateAction<Customer> updateAction, final UpdateAction<Customer>... updateActions) {
        final List<UpdateAction<Customer>> actions = new ArrayList<>();
        actions.add(updateAction);
        actions.addAll(Arrays.asList(updateActions));
        return new CustomerInStoreUpdateCommandImpl(versioned, storeKey, actions);
    }

    static CustomerInStoreUpdateCommand ofKey(final String key, final Long version, final String storeKey, final List<? extends UpdateAction<Customer>> updateActions) {
        final Versioned<Customer> versioned = Versioned.of("key=" + urlEncode(key), version);
        return new CustomerInStoreUpdateCommandImpl(versioned, storeKey, updateActions);
    }

    static CustomerInStoreUpdateCommand ofKey(final String key, final Long version, final String storeKey, final UpdateAction<Customer> updateAction) {
        return ofKey(key, version, storeKey, Collections.singletonList(updateAction));
    }
}
