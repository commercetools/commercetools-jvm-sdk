package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.models.Versioned;

public interface InventoryEntryDeleteCommand extends ByIdDeleteCommand<InventoryEntry>, MetaModelExpansionDsl<InventoryEntry, InventoryEntryDeleteCommand, InventoryEntryExpansionModel<InventoryEntry>> {
    static InventoryEntryDeleteCommand of(final Versioned<InventoryEntry> versioned) {
        return new InventoryEntryDeleteCommandImpl(versioned);
    }
}
