package sphere;

import io.sphere.client.shop.model.InventoryEntry;

/**
 * Sphere HTTP API for retrieving product inventory information.
 */
public interface InventoryService {
    FetchRequest<InventoryEntry> bySku(String sku);
    FetchRequest<InventoryEntry> bySku(String sku, String channelId);
    QueryRequest<InventoryEntry> queryBySku(String sku);
    QueryRequest<InventoryEntry> query();
}
