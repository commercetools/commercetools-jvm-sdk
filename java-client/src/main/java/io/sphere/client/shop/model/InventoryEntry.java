package io.sphere.client.shop.model;

import com.google.common.base.Optional;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Reference;
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
    private Reference<Catalog> catalog = EmptyReference.create("catalog");
    private String sku;
    private Integer restockableInDays;

    // for JSON deserializer
    protected InventoryEntry() {}

    /** Unique id of this inventory entry. */
    public String getId() { return id; }

    /** Version of this inventory entry. */
    public int getVersion() { return version; }

    /** The product id to which the inventory entry belongs. */
    public String getProductId() { return productId; }

    /** The product variant id to which the inventory entry belongs. */
    public String getVariantId() { return variantId; }

    /** Current quantity on stock. */
    public int getQuantityOnStock() { return quantityOnStock; }

    /** Current available quantity (quantity on stock minus the quantity of reserved items). */
    public int getAvailableQuantity() { return availableQuantity; }

    /** The catalog to which the inventory entry belongs.
     * @return The catalog of null in case of the master catalog. */
    public Reference<Catalog> getCatalog() { return catalog; }

    /** The SKU of the product to which the inventory entry belongs. */
    public String getSku() { return sku; }

    /** The number of days required to restock the product variant.
     * @return The number of days or {@link Optional#absent()} if not specified. */
    public Optional<Integer> getRestockableInDays() { return Optional.fromNullable(restockableInDays); }
}
