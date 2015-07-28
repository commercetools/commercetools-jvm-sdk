package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.InventoryEntryDraft;

final class InventoryEntryCreateCommandImpl extends CreateCommandImpl<InventoryEntry, InventoryEntryDraft> implements InventoryEntryCreateCommand {
    InventoryEntryCreateCommandImpl(final InventoryEntryDraft body) {
        super(body, InventoryEntryEndpoint.ENDPOINT);
    }
}
