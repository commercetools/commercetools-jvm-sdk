package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class InventoryEntryByIdFetchImpl extends MetaModelFetchDslImpl<InventoryEntry, InventoryEntry, InventoryEntryByIdFetch, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryByIdFetch {
    InventoryEntryByIdFetchImpl(final String id) {
        super(id, InventoryEntryEndpoint.ENDPOINT, InventoryEntryExpansionModel.of(), InventoryEntryByIdFetchImpl::new);
    }

    public InventoryEntryByIdFetchImpl(MetaModelFetchDslBuilder<InventoryEntry, InventoryEntry, InventoryEntryByIdFetch, InventoryEntryExpansionModel<InventoryEntry>> builder) {
        super(builder);
    }
}

