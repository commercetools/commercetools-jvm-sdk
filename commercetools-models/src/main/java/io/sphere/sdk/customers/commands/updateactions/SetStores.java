package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

import java.util.List;

public final class SetStores extends UpdateActionImpl<Customer> {

    private final List<ResourceIdentifier<Store>> stores;
    
    private SetStores(final List<ResourceIdentifier<Store>> stores) {
        super("setStores");
        this.stores = stores;
    }

    public static SetStores of(final List<ResourceIdentifier<Store>> stores) {
        return new SetStores(stores);
    }
    
    public List<ResourceIdentifier<Store>> getStores() {
        return stores;
    }
}