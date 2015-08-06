package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.queries.*;

public interface InventoryEntryQueryModel extends ResourceQueryModel<InventoryEntry> {
    StringQuerySortingModel<InventoryEntry> sku();

    ReferenceQueryModel<InventoryEntry, Channel> supplyChannel();

    LongQuerySortingModel<InventoryEntry> quantityOnStock();

    LongQuerySortingModel<InventoryEntry> availableQuantity();

    IntegerQuerySortingModel<InventoryEntry> restockableInDays();
}
