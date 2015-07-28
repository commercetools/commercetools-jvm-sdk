package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;

/**
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryCreateCommandTest#execution()}
 */
public interface InventoryEntryCreateCommand extends CreateCommand<InventoryEntry> {
    static InventoryEntryCreateCommand of(final InventoryEntryDraft body) {
        return new InventoryEntryCreateCommandImpl(body);
    }
}
