package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

public final class RemoveStore extends UpdateActionImpl<Customer> {

    private final ResourceIdentifier<Store> store;
    
    private RemoveStore(final ResourceIdentifier<Store> store) {
        super("removeStore");
        this.store = store;
    }

    public static RemoveStore of(final ResourceIdentifier<Store> store) {
        return new RemoveStore(store);
    }

    public ResourceIdentifier<Store> getStore() {
        return store;
    }
}
