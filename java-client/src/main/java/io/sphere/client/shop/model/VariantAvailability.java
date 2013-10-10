package io.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonProperty;

/** Stock availability of a variant in a product catalog.
 *
 * <p>This information is replicated from variant's {@link InventoryEntry inventory entry}, purely as an optimization.
 * {@link #getRestockableInDays() getRestockableInDays} matches
 * {@link io.sphere.client.shop.model.InventoryEntry#getRestockableInDays() InventoryEntry.getRestockableInDays},
 * {@link #isOnStock() isOnStock} is true if and only if
 * {@link io.sphere.client.shop.model.InventoryEntry#getQuantityOnStock() InventoryEntry.quantityOnStock} is greater than 0.
 *
 * <p><i>Note:<i/>The corresponding InventoryEntry is the immediate source of truth. The information in VariantAvailability
 * is updated asynchronously when inventory entries are modified, and is therefore eventually consistent.
 * */
public class VariantAvailability {
    @JsonProperty("isOnStock") private boolean isOnStock;
    private int restockableInDays;

    // for JSON deserializer
    private VariantAvailability() { }

    /** True if the quantity on stock for the product variant is greater than 0. */
    public boolean isOnStock() { return isOnStock; }

    /** The number of days required to restock the product variant. */
    public int getRestockableInDays() { return restockableInDays; }

    @Override
    public String toString() {
        return "VariantAvailability{" +
                "isOnStock=" + isOnStock +
                ", restockableInDays=" + restockableInDays +
                '}';
    }
}
