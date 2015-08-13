package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;

/**
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryCreateCommandTest#execution()}
 */
public interface InventoryEntryCreateCommand extends CreateCommand<InventoryEntry>, MetaModelExpansionDsl<InventoryEntry, InventoryEntryCreateCommand, InventoryEntryExpansionModel<InventoryEntry>> {
    static InventoryEntryCreateCommand of(final InventoryEntryDraft body) {
        return new InventoryEntryCreateCommandImpl(body);
    }
}
