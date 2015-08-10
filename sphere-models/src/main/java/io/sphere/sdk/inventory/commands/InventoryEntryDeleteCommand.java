package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.models.Versioned;

public interface InventoryEntryDeleteCommand extends ByIdDeleteCommand<InventoryEntry> {
    static DeleteCommand<InventoryEntry> of(final Versioned<InventoryEntry> versioned) {
        return new InventoryEntryDeleteCommandImpl(versioned);
    }
}
