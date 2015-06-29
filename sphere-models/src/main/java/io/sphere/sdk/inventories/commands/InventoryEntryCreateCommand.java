package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.InventoryEntryDraft;

/**
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryCreateCommandTest#execution()}
 */
public class InventoryEntryCreateCommand extends CreateCommandImpl<InventoryEntry, InventoryEntryDraft> {
    private InventoryEntryCreateCommand(final InventoryEntryDraft body) {
        super(body, InventoryEntryEndpoint.ENDPOINT);
    }

    public static InventoryEntryCreateCommand of(final InventoryEntryDraft body) {
        return new InventoryEntryCreateCommand(body);
    }
}
