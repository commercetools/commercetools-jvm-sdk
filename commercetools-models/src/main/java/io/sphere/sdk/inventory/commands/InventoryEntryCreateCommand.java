package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;

/**
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryCreateCommandIntegrationTest#execution()}
 */
public interface InventoryEntryCreateCommand extends DraftBasedCreateCommand<InventoryEntry, InventoryEntryDraft>, MetaModelReferenceExpansionDsl<InventoryEntry, InventoryEntryCreateCommand, InventoryEntryExpansionModel<InventoryEntry>> {
    static InventoryEntryCreateCommand of(final InventoryEntryDraft body) {
        return new InventoryEntryCreateCommandImpl(body);
    }
}
