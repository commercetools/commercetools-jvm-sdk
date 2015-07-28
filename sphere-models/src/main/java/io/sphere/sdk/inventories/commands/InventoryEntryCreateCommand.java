package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.InventoryEntryDraft;

/**
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryCreateCommandTest#execution()}
 */
public interface InventoryEntryCreateCommand extends CreateCommand<InventoryEntry> {
    static InventoryEntryCreateCommand of(final InventoryEntryDraft body) {
        return new InventoryEntryCreateCommandImpl(body);
    }
}
