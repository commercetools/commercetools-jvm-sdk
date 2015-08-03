package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.inventory.InventoryEntry;

/**
 *
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommandTest#removeQuantity()}
 */
public class RemoveQuantity extends UpdateActionImpl<InventoryEntry> {
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
