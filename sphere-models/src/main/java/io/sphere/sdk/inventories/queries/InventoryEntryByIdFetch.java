package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ByIdFetchImpl;

public class InventoryEntryByIdFetch extends ByIdFetchImpl<InventoryEntry> {
    private InventoryEntryByIdFetch(final String id) {
        super(id, InventoryEntryEndpoint.ENDPOINT);
    }

    public static InventoryEntryByIdFetch of(final Identifiable<InventoryEntry> inventoryEntry) {
        return of(inventoryEntry.getId());
    }

    public static InventoryEntryByIdFetch of(final String id) {
        return new InventoryEntryByIdFetch(id);
    }

    public static InventoryEntryExpansionModel<InventoryEntry> expansionPath() {
        return new InventoryEntryExpansionModel<>();
    }
}
