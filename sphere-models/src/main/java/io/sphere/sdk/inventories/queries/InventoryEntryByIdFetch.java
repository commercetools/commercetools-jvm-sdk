package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface InventoryEntryByIdFetch extends MetaModelFetchDsl<InventoryEntry, InventoryEntryByIdFetch, InventoryEntryExpansionModel<InventoryEntry>> {
    static InventoryEntryByIdFetch of(final Identifiable<InventoryEntry> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static InventoryEntryByIdFetch of(final String id) {
        return new InventoryEntryByIdFetchImpl(id);
    }

    @Override
    InventoryEntryByIdFetch plusExpansionPath(final Function<InventoryEntryExpansionModel<InventoryEntry>, ExpansionPath<InventoryEntry>> m);

    @Override
    InventoryEntryByIdFetch withExpansionPath(final Function<InventoryEntryExpansionModel<InventoryEntry>, ExpansionPath<InventoryEntry>> m);

    @Override
    List<ExpansionPath<InventoryEntry>> expansionPaths();

    @Override
    InventoryEntryByIdFetch plusExpansionPath(final ExpansionPath<InventoryEntry> expansionPath);

    @Override
    InventoryEntryByIdFetch withExpansionPath(final ExpansionPath<InventoryEntry> expansionPath);

    @Override
    InventoryEntryByIdFetch withExpansionPath(final List<ExpansionPath<InventoryEntry>> expansionPaths);
}

