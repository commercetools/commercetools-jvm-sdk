package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventory.InventoryEntry;

/**
 *
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryUpdateCommandTest#addQuantity()}
 */
public class AddQuantity extends UpdateAction<InventoryEntry> {
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
