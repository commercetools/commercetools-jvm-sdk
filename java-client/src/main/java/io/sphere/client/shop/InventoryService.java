package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.Reference;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.InventoryEntry;
import io.sphere.client.shop.model.InventoryEntryUpdate;
import io.sphere.client.shop.model.SupplyChannel;
import org.joda.time.DateTime;

/**
 * Sphere HTTP API for retrieving product inventory information.
 */
public interface InventoryService {
    /** Creates a new InventoryEntry that does not belong to a SupplyChannel.
     *
     * Throws a io.sphere.client.exceptions.DuplicateSkuException if the sku is already present.
     **/
    CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock);

    /** Creates a new InventoryEntry that does not belong to a SupplyChannel.
     *
     * Throws a io.sphere.client.exceptions.DuplicateSkuException if the sku is already present.
     *
     * @param expectedDelivery optional expected date for the delivery of the item.
     * @param restockableInDays optional the time difference in days to get the item in stock again.
     **/
    CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock, Long restockableInDays, DateTime expectedDelivery);

    /** Creates a new InventoryEntry that does belong to a SupplyChannel.
     *
     * Throws a io.sphere.client.exceptions.DuplicateSkuException if the sku is already present.
     * Throws a io.sphere.client.SphereError.ResourceNotFound if the SupplyChannel does not exist.
     *
     * @param expectedDelivery optional expected date for the delivery of the item.
     * @param restockableInDays optional the time difference in days to get the item in stock again.
     **/
    CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock, Long restockableInDays, DateTime expectedDelivery, String supplyChannelId);

    /** Fetches the InventoryEntry by sku which does not have any supply channel. */
    FetchRequest<InventoryEntry> bySku(String sku);

    /** Fetches the InventoryEntry by sku which does have a supply channel. */
    FetchRequest<InventoryEntry> bySku(String sku, String supplyChannelId);

    /** Queries inventory entries. */
    QueryRequest<InventoryEntry> query();

    CommandRequest<InventoryEntry> updateInventoryEntry(VersionedId id, InventoryEntryUpdate update);
}
