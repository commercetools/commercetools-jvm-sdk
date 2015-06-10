package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class InventoryEntryByIdFetchImpl extends MetaModelFetchDslImpl<InventoryEntry, InventoryEntryByIdFetch, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryByIdFetch {
    InventoryEntryByIdFetchImpl(final String id) {
        super(id, InventoryEntryEndpoint.ENDPOINT, InventoryEntryExpansionModel.of(), InventoryEntryByIdFetchImpl::new);
    }

    public InventoryEntryByIdFetchImpl(MetaModelFetchDslBuilder<InventoryEntry, InventoryEntryByIdFetch, InventoryEntryExpansionModel<InventoryEntry>> builder) {
        super(builder);
    }
}

