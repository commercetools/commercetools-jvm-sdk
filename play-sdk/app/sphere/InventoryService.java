package sphere;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.InventoryEntry;

import io.sphere.client.shop.model.InventoryEntryUpdate;
import play.libs.F.Promise;

/**
 * Sphere HTTP API for retrieving product inventory information.
 */
public interface InventoryService {
    FetchRequest<InventoryEntry> bySku(String sku);
    QueryRequest<InventoryEntry> query();
    InventoryEntry updateInventoryEntry(VersionedId id, InventoryEntryUpdate update);
    Promise<SphereResult<InventoryEntry>> updateInventoryEntryAsync(VersionedId id, InventoryEntryUpdate update);
}
