package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface InventoryEntryByIdFetch extends MetaModelFetchDsl<InventoryEntry, InventoryEntryByIdFetch, InventoryEntryExpansionModel<InventoryEntry>> {
    static InventoryEntryByIdFetch of(final Identifiable<InventoryEntry> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static InventoryEntryByIdFetch of(final String id) {
        return new InventoryEntryByIdFetchImpl(id);
    }
}

