package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.inventory.InventoryEntry;

/**
 *
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommandTest#changeQuantity()}
 */
public class ChangeQuantity extends UpdateActionImpl<InventoryEntry> {
    private final long quantity;

    private ChangeQuantity(final long quantity) {
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
