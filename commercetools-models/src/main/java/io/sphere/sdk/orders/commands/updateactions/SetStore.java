package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Sets the locale.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#locale()}
 */
public final class SetStore extends UpdateActionImpl<Order> {
    @Nullable
    private final ResourceIdentifier<Store> store;

    private SetStore(@Nullable final ResourceIdentifier<Store> store) {
        super("setStore");
        this.store = store;
    }

    public static SetStore of(@Nullable final ResourceIdentifier<Store> store) {
        return new SetStore(store);
    }

    @Nullable
    public ResourceIdentifier<Store> getStore() {
        return store;
    }
}