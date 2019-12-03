package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

public final class AddStore extends UpdateActionImpl<Customer> {

    private final ResourceIdentifier<Store> store;

    private AddStore(final ResourceIdentifier<Store> store) {
        super("addStore");
        this.store = store;
    }

    public static AddStore of(final ResourceIdentifier<Store> store) {
        return new AddStore(store);
    }

    public ResourceIdentifier<Store> getStore() {
        return store;
    }
    
}
