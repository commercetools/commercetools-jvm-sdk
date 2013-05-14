package io.sphere.client.shop.model;

import com.google.common.base.Optional;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Reference;
import io.sphere.client.model.VersionedId;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;

/** Inventory information for a specific product variant in a catalog. */
@Immutable
@JsonIgnoreProperties("reservations")
public class InventoryEntry {
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    @Nonnull private String productId;
    private int variantId;
    private int quantityOnStock;
    private int availableQuantity;
    @Nonnull private Reference<Catalog> catalog = EmptyReference.create("catalog");
    private String sku = "";
    private Integer restockableInDays;

    // for JSON deserializer
    protected InventoryEntry() {}

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** The product id to which the inventory entry belongs. */
    @Nonnull public String getProductId() { return productId; }

    /** The product variant id to which the inventory entry belongs. */
    public int getVariantId() { return variantId; }

    /** Current quantity on stock. */
    public int getQuantityOnStock() { return quantityOnStock; }

    /** Current available quantity (quantity on stock minus the quantity of reserved items). */
    public int getAvailableQuantity() { return availableQuantity; }

    /** The catalog to which the inventory entry belongs.
     * @return The catalog of null in case of the master catalog. */
    @Nonnull public Reference<Catalog> getCatalog() { return catalog; }

    /** The SKU of the product to which the inventory entry belongs. */
    public String getSku() { return sku; }

    /** The number of days required to restock the product variant.
     *  @return The number of days or {@link Optional#absent()} if not specified. */
    @Nonnull public Optional<Integer> getRestockableInDays() { return Optional.fromNullable(restockableInDays); }
}
