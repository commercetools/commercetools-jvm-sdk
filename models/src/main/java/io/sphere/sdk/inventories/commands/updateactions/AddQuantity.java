package io.sphere.sdk.inventories.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventories.InventoryEntry;

/**
 *
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryUpdateCommandTest#addQuantity()}
 */
public class AddQuantity extends UpdateAction<InventoryEntry> {
    private final long quantity;

    private AddQuantity(final long quantity) {
        super("addQuantity");
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public static AddQuantity of(final long quantity) {
        return new AddQuantity(quantity);
    }
}
