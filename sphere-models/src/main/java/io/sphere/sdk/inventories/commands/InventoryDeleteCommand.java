package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.models.Versioned;

public interface InventoryDeleteCommand extends ByIdDeleteCommand<InventoryEntry> {
    static DeleteCommand<InventoryEntry> of(final Versioned<InventoryEntry> versioned) {
        return new InventoryEntryDeleteCommandImpl(versioned);
    }
}
