package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public interface InventoryEntryQueryModel extends ResourceQueryModel<InventoryEntry>, WithCustomQueryModel<InventoryEntry> {
    StringQuerySortingModel<InventoryEntry> sku();

    ReferenceQueryModel<InventoryEntry, Channel> supplyChannel();

    LongQuerySortingModel<InventoryEntry> quantityOnStock();

    LongQuerySortingModel<InventoryEntry> availableQuantity();

    IntegerQuerySortingModel<InventoryEntry> restockableInDays();

    @Override
    CustomQueryModel<InventoryEntry> custom();
}
