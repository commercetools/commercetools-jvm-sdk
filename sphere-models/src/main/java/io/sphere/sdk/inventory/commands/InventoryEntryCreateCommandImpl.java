package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;

final class InventoryEntryCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<InventoryEntry, InventoryEntryCreateCommand, InventoryEntryDraft, InventoryEntryExpansionModel<InventoryEntry>> implements InventoryEntryCreateCommand {
    InventoryEntryCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<InventoryEntry, InventoryEntryCreateCommand, InventoryEntryDraft, InventoryEntryExpansionModel<InventoryEntry>> builder) {
        super(builder);
    }

    InventoryEntryCreateCommandImpl(final InventoryEntryDraft body) {
        super(body, InventoryEntryEndpoint.ENDPOINT, InventoryEntryExpansionModel.of(), InventoryEntryCreateCommandImpl::new);
    }
}
