package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.queries.IntegerQuerySortingModel;
import io.sphere.sdk.queries.LongQuerySortingModel;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public interface InventoryEntryQueryModel {
    StringQuerySortingModel<InventoryEntry> sku();

    ReferenceQueryModel<InventoryEntry, Channel> supplyChannel();

    LongQuerySortingModel<InventoryEntry> quantityOnStock();

    LongQuerySortingModel<InventoryEntry> availableQuantity();

    IntegerQuerySortingModel<InventoryEntry> restockableInDays();
}
