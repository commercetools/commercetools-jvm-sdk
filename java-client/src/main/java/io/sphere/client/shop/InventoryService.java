package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.InventoryEntry;
import io.sphere.client.shop.model.InventoryEntryUpdate;
import org.joda.time.DateTime;

/**
 * Sphere HTTP API for retrieving product inventory information.
 */
public interface InventoryService {
    /** Creates a new InventoryEntry that does not belong to a Channel.
     *
     * Throws a io.sphere.client.exceptions.DuplicateSkuException if the sku is already present.
     **/
    CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock);

    /** Creates a new InventoryEntry that does not belong to a Channel.
     *
     * Throws a io.sphere.client.exceptions.DuplicateSkuException if the sku is already present.
     *
     * @param expectedDelivery optional expected date for the delivery of the item.
     * @param restockableInDays optional the time difference in days to get the item in stock again.
     **/
    CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock, Long restockableInDays, DateTime expectedDelivery);

    /** Creates a new InventoryEntry that does belong to a Channel.
     *
     * Throws a io.sphere.client.exceptions.DuplicateSkuException if the sku is already present.
     * Throws a io.sphere.client.SphereError.ResourceNotFound if the Channel does not exist.
     *
     * @param expectedDelivery optional expected date for the delivery of the item.
     * @param restockableInDays optional the time difference in days to get the item in stock again.
     **/
    CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock, Long restockableInDays, DateTime expectedDelivery, String channelId);

    /** Fetches the InventoryEntry by sku which does not have any channel. */
    FetchRequest<InventoryEntry> bySku(String sku);

    /** Fetches the InventoryEntry by sku which does have a channel. */
    FetchRequest<InventoryEntry> bySku(String sku, String channelId);

    /** Searches all InventoryEntry objects that have the given sku. Finds the objects with and without Channel. */
    QueryRequest<InventoryEntry> queryBySku(String sku);

    /** Queries inventory entries. */
    QueryRequest<InventoryEntry> query();

    CommandRequest<InventoryEntry> updateInventoryEntry(VersionedId id, InventoryEntryUpdate update);
}
