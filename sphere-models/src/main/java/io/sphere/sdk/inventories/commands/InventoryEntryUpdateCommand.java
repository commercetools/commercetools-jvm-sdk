package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public interface InventoryEntryUpdateCommand extends UpdateCommandDsl<InventoryEntry, InventoryEntryUpdateCommand> {
    static InventoryEntryUpdateCommand of(final Versioned<InventoryEntry> versioned, final UpdateAction<InventoryEntry> updateAction) {
        return of(versioned, asList(updateAction));
    }

    static InventoryEntryUpdateCommand of(final Versioned<InventoryEntry> versioned, final List<? extends UpdateAction<InventoryEntry>> updateActions) {
        return new InventoryEntryUpdateCommandImpl(versioned, updateActions);
    }
}
