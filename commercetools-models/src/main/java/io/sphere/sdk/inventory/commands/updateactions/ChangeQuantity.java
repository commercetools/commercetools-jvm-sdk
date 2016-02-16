package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.inventory.InventoryEntry;

/**
 * Changes the absolute quantity.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommandIntegrationTest#changeQuantity()}
 */
public final class ChangeQuantity extends UpdateActionImpl<InventoryEntry> {
    private final Long quantity;

    private ChangeQuantity(final Long quantity) {
        super("changeQuantity");
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public static ChangeQuantity of(final long quantity) {
        return new ChangeQuantity(quantity);
    }
}
