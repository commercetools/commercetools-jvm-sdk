package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface InventoryEntryByIdFetch extends MetaModelFetchDsl<InventoryEntry, InventoryEntry, InventoryEntryByIdFetch, InventoryEntryExpansionModel<InventoryEntry>> {
    static InventoryEntryByIdFetch of(final Identifiable<InventoryEntry> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static InventoryEntryByIdFetch of(final String id) {
        return new InventoryEntryByIdFetchImpl(id);
    }

    @Override
    InventoryEntryByIdFetch plusExpansionPaths(final Function<InventoryEntryExpansionModel<InventoryEntry>, ExpansionPath<InventoryEntry>> m);

    @Override
    InventoryEntryByIdFetch withExpansionPaths(final Function<InventoryEntryExpansionModel<InventoryEntry>, ExpansionPath<InventoryEntry>> m);

    @Override
    List<ExpansionPath<InventoryEntry>> expansionPaths();

    @Override
    InventoryEntryByIdFetch plusExpansionPaths(final ExpansionPath<InventoryEntry> expansionPath);

    @Override
    InventoryEntryByIdFetch withExpansionPaths(final ExpansionPath<InventoryEntry> expansionPath);

    @Override
    InventoryEntryByIdFetch withExpansionPaths(final List<ExpansionPath<InventoryEntry>> expansionPaths);
}

