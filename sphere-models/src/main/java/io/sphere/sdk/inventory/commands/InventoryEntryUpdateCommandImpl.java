package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.models.Versioned;

import java.util.List;


final class InventoryEntryUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<InventoryEntry, InventoryEntryUpdateCommand, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryUpdateCommand {
    InventoryEntryUpdateCommandImpl(final Versioned<InventoryEntry> versioned, final List<? extends UpdateAction<InventoryEntry>> updateActions) {
        super(versioned, updateActions, InventoryEntryEndpoint.ENDPOINT, InventoryEntryUpdateCommandImpl::new, InventoryEntryExpansionModel.of());
    }

    InventoryEntryUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<InventoryEntry, InventoryEntryUpdateCommand, InventoryEntryExpansionModel<InventoryEntry>> builder) {
        super(builder);
    }
}
