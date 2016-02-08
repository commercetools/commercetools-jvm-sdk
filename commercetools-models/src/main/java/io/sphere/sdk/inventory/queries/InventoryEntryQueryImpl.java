package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;


final class InventoryEntryQueryImpl extends MetaModelQueryDslImpl<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryQuery {
    InventoryEntryQueryImpl(){
        super(InventoryEntryEndpoint.ENDPOINT.endpoint(), InventoryEntryQuery.resultTypeReference(), InventoryEntryQueryModelImpl.of(), InventoryEntryExpansionModel.of(), InventoryEntryQueryImpl::new);
    }

    private InventoryEntryQueryImpl(final MetaModelQueryDslBuilder<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel, InventoryEntryExpansionModel<InventoryEntry>> builder) {
        super(builder);
    }
}