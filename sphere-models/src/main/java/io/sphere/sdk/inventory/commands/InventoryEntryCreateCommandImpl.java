package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;

final class InventoryEntryCreateCommandImpl extends CreateCommandImpl<InventoryEntry, InventoryEntryDraft> implements InventoryEntryCreateCommand {
    InventoryEntryCreateCommandImpl(final InventoryEntryDraft body) {
        super(body, InventoryEntryEndpoint.ENDPOINT);
    }
}
