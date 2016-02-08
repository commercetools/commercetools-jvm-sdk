package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

public interface InventoryEntryByIdGet extends MetaModelGetDsl<InventoryEntry, InventoryEntry, InventoryEntryByIdGet, InventoryEntryExpansionModel<InventoryEntry>> {
    static InventoryEntryByIdGet of(final Identifiable<InventoryEntry> inventoryEntry) {
        return of(inventoryEntry.getId());
    }

    static InventoryEntryByIdGet of(final String id) {
        return new InventoryEntryByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<InventoryEntry>> expansionPaths();

    @Override
    InventoryEntryByIdGet plusExpansionPaths(final ExpansionPath<InventoryEntry> expansionPath);

    @Override
    InventoryEntryByIdGet withExpansionPaths(final ExpansionPath<InventoryEntry> expansionPath);

    @Override
    InventoryEntryByIdGet withExpansionPaths(final List<ExpansionPath<InventoryEntry>> expansionPaths);
}

