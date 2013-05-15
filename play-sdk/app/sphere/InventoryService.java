package sphere;

import io.sphere.client.shop.model.InventoryEntry;

/** Sphere HTTP API for retrieving product inventory information. */
public interface InventoryService {
    /** Finds an inventory entry by id. */
    FetchRequest<InventoryEntry> byId(String id);

    /** Finds an inventory entry for given product variant. */
    FetchRequest<InventoryEntry> byProductVariant(String productId, int variantId);
}
