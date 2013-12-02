package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.InventoryEntry;

/**
 * Sphere HTTP API for retrieving product inventory information.
 */
public interface InventoryService {
    CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock);
    /** Fetches the InventoryEntry by sku which does not have any supply channel */
    FetchRequest<InventoryEntry> bySku(String sku);
    /** Queries inventory entries. */
    QueryRequest<InventoryEntry> query();
}
