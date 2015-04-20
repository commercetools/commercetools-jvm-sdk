package io.sphere.sdk.inventories.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventories.InventoryEntry;

/**
 *
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryUpdateCommandTest#removeQuantity()}
 */
public class RemoveQuantity extends UpdateAction<InventoryEntry> {
    private final long quantity;

    private RemoveQuantity(final long quantity) {
        super("removeQuantity");
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public static RemoveQuantity of(final long quantity) {
        return new RemoveQuantity(quantity);
    }
}
