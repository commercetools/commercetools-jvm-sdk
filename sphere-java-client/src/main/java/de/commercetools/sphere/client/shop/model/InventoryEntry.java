package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Reference;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/** Inventory information for a specific product variant in a catalog. */
@Immutable
@JsonIgnoreProperties("reservations")
public class InventoryEntry {
    private String id;
    private int version;
    private String productId;
    private String variantId;
    private int quantityOnStock;
    private int availableQuantity;
    private Reference<Catalog> catalog;
    private String sku;
    private int restockableInDays;

    // for JSON deserializer
    protected InventoryEntry() {}

    /** Unique id of this inventory entry. */
    public String getId() { return id; }

    /** Version of this inventory entry that increases when the customer is modified. */
    public int getVersion() { return version; }

    /** The product id to which the inventory entry belongs. */
    public String getProductId() { return productId; }

    /** The product variant id to which the inventory entry belongs. */
    public String getVariantId() { return variantId; }

    /** Available quantity on stock. */
    public int getQuantityOnStock() { return quantityOnStock; }

    /** Available quantity (quantity on stock minus the quantity of the reserved items)*/
    public int getAvailableQuantity() { return availableQuantity; }

    /** The catalog to which this inventory entry belongs. Returns null for master catalog. */
    public Reference<Catalog> getCatalog() { return catalog; }

    /** The sku of the product this inventory entry references. */
    public String getSku() { return sku; }

    /** The number of days required to restock the item. Returns null if the item can not be restocked. */
    public int getRestockableInDays() { return restockableInDays; }
}
