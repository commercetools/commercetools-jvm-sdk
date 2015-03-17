package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class InventoryEntryUpdateCommand extends UpdateCommandDslImpl<InventoryEntry> {
    private InventoryEntryUpdateCommand(final Versioned<InventoryEntry> versioned, final List<? extends UpdateAction<InventoryEntry>> updateActions) {
        super(versioned, updateActions, Endpoint.ENDPOINT);
    }

    public static InventoryEntryUpdateCommand of(final Versioned<InventoryEntry> versioned, final UpdateAction<InventoryEntry> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static InventoryEntryUpdateCommand of(final Versioned<InventoryEntry> versioned, final List<? extends UpdateAction<InventoryEntry>> updateActions) {
        return new InventoryEntryUpdateCommand(versioned, updateActions);
    }
}
