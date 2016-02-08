package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface InventoryEntryUpdateCommand extends UpdateCommandDsl<InventoryEntry, InventoryEntryUpdateCommand>, MetaModelReferenceExpansionDsl<InventoryEntry, InventoryEntryUpdateCommand, InventoryEntryExpansionModel<InventoryEntry>> {
    static InventoryEntryUpdateCommand of(final Versioned<InventoryEntry> versioned, final UpdateAction<InventoryEntry> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static InventoryEntryUpdateCommand of(final Versioned<InventoryEntry> versioned, final List<? extends UpdateAction<InventoryEntry>> updateActions) {
        return new InventoryEntryUpdateCommandImpl(versioned, updateActions);
    }
}
