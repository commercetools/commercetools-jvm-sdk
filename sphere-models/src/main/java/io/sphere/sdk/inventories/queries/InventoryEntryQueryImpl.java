package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;


final class InventoryEntryQueryImpl extends UltraQueryDslImpl<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel<InventoryEntry>, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryQuery {
    InventoryEntryQueryImpl(){
        super(InventoryEntryEndpoint.ENDPOINT.endpoint(), InventoryEntryQuery.resultTypeReference(), InventoryEntryQueryModel.of(), InventoryEntryExpansionModel.of(), InventoryEntryQueryImpl::new);
    }

    private InventoryEntryQueryImpl(final UltraQueryDslBuilder<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel<InventoryEntry>, InventoryEntryExpansionModel<InventoryEntry>> builder) {
        super(builder);
    }
}