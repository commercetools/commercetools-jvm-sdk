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
    private String sku = "";
    private long quantityOnStock;
    private long availableQuantity;
    private Integer restockableInDays;

    // for JSON deserializer
    protected InventoryEntry() {}

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** Current quantity on stock. */
    public long getQuantityOnStock() { return quantityOnStock; }

    /** Current available quantity (quantity on stock minus the quantity of reserved items). */
    public long getAvailableQuantity() { return availableQuantity; }

    /** The SKU of the product to which the inventory entry belongs. */
    public String getSku() { return sku; }

    /** The number of days required to restock the product variant.
     *  @return The number of days or {@link Optional#absent()} if not specified. */
    @Nonnull public Optional<Integer> getRestockableInDays() { return Optional.fromNullable(restockableInDays); }

    @Override
    public String toString() {
        return "InventoryEntry{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", sku='" + sku + '\'' +
                ", quantityOnStock=" + quantityOnStock +
                ", availableQuantity=" + availableQuantity +
                ", restockableInDays=" + restockableInDays +
                '}';
    }
}
