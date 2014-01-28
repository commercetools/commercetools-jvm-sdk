package io.sphere.client.shop.model;

import com.google.common.base.Optional;
import io.sphere.client.model.Reference;
import io.sphere.client.model.VersionedId;
import io.sphere.internal.util.Iso8601JsonSerializer;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;
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
    @JsonSerialize(using = Iso8601JsonSerializer.class)
    private DateTime expectedDelivery;
    Reference<Channel> channel = Channel.emptyReference();

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

    public DateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    public Reference<Channel> getChannel() {
        return channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryEntry that = (InventoryEntry) o;

        if (availableQuantity != that.availableQuantity) return false;
        if (quantityOnStock != that.quantityOnStock) return false;
        if (version != that.version) return false;
        if (!channel.equals(that.channel)) return false;
        if (expectedDelivery != null ? !expectedDelivery.equals(that.expectedDelivery) : that.expectedDelivery != null)
            return false;
        if (!id.equals(that.id)) return false;
        if (restockableInDays != null ? !restockableInDays.equals(that.restockableInDays) : that.restockableInDays != null)
            return false;
        if (!sku.equals(that.sku)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + version;
        result = 31 * result + sku.hashCode();
        result = 31 * result + (int) (quantityOnStock ^ (quantityOnStock >>> 32));
        result = 31 * result + (int) (availableQuantity ^ (availableQuantity >>> 32));
        result = 31 * result + (restockableInDays != null ? restockableInDays.hashCode() : 0);
        result = 31 * result + (expectedDelivery != null ? expectedDelivery.hashCode() : 0);
        result = 31 * result + channel.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InventoryEntry{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", sku='" + sku + '\'' +
                ", quantityOnStock=" + quantityOnStock +
                ", availableQuantity=" + availableQuantity +
                ", restockableInDays=" + restockableInDays +
                ", expectedDelivery=" + expectedDelivery +
                ", channel=" + channel +
                '}';
    }
}
