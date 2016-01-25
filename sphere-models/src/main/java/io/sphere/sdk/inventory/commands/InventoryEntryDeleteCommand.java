package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.models.Versioned;

public interface InventoryEntryDeleteCommand extends MetaModelReferenceExpansionDsl<InventoryEntry, InventoryEntryDeleteCommand, InventoryEntryExpansionModel<InventoryEntry>>, DeleteCommand<InventoryEntry> {
    static InventoryEntryDeleteCommand of(final Versioned<InventoryEntry> versioned) {
        return new InventoryEntryDeleteCommandImpl(versioned);
    }
}
