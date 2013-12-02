package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.shop.model.InventoryEntry;

/**
 * Sphere HTTP API for retrieving product inventory information.
 */
public interface InventoryService {
    CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock);
}
