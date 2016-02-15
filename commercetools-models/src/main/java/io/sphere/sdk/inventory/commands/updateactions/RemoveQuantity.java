package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.inventory.InventoryEntry;

/**
 * Subtracts quantity from the stock.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommandIntegrationTest#removeQuantity()}
 */
public final class RemoveQuantity extends UpdateActionImpl<InventoryEntry> {
    private final Long quantity;

    private RemoveQuantity(final Long quantity) {
        super("removeQuantity");
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public static RemoveQuantity of(final long quantity) {
        return new RemoveQuantity(quantity);
    }
}
