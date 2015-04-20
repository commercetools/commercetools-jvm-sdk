package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.models.Versioned;

public class InventoryDeleteCommand extends ByIdDeleteCommandImpl<InventoryEntry> {
    private InventoryDeleteCommand(final Versioned<InventoryEntry> versioned) {
        super(versioned, Endpoint.ENDPOINT);
    }

    public static DeleteCommand<InventoryEntry> of(final Versioned<InventoryEntry> versioned) {
        return new InventoryDeleteCommand(versioned);
    }
}
