package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.models.Versioned;

final class InventoryEntryDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<InventoryEntry, InventoryEntryDeleteCommand, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryDeleteCommand {
    InventoryEntryDeleteCommandImpl(final Versioned<InventoryEntry> versioned) {
        super(versioned, InventoryEntryEndpoint.ENDPOINT, InventoryEntryExpansionModel.of(), InventoryEntryDeleteCommandImpl::new);
    }

    InventoryEntryDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<InventoryEntry, InventoryEntryDeleteCommand, InventoryEntryExpansionModel<InventoryEntry>> builder) {
        super(builder);
    }
}
