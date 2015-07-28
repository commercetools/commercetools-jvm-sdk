package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.models.Versioned;

final class InventoryEntryDeleteCommandImpl extends ByIdDeleteCommandImpl<InventoryEntry> implements InventoryDeleteCommand {
    InventoryEntryDeleteCommandImpl(final Versioned<InventoryEntry> versioned) {
        super(versioned, InventoryEntryEndpoint.ENDPOINT);
    }
}
