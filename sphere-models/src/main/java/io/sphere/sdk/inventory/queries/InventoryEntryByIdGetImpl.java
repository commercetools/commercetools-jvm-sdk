package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class InventoryEntryByIdGetImpl extends MetaModelGetDslImpl<InventoryEntry, InventoryEntry, InventoryEntryByIdGet, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryByIdGet {
    InventoryEntryByIdGetImpl(final String id) {
        super(id, InventoryEntryEndpoint.ENDPOINT, InventoryEntryExpansionModel.of(), InventoryEntryByIdGetImpl::new);
    }

    public InventoryEntryByIdGetImpl(MetaModelGetDslBuilder<InventoryEntry, InventoryEntry, InventoryEntryByIdGet, InventoryEntryExpansionModel<InventoryEntry>> builder) {
        super(builder);
    }
}

