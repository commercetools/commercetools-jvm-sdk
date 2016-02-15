package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.inventory.InventoryEntry;

/**
 * Adds items to the stock.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommandIntegrationTest#addQuantity()}
 *
 * @see InventoryEntry#getQuantityOnStock()
 */
public final class AddQuantity extends UpdateActionImpl<InventoryEntry> {
    private final Long quantity;

    private AddQuantity(final Long quantity) {
        super("addQuantity");
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public static AddQuantity of(final long quantity) {
        return new AddQuantity(quantity);
    }
}
