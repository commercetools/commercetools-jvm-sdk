package io.sphere.sdk.inventory.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.inventory.InventoryEntry;

public interface InventoryEntryExpansionModel<T> {
    ChannelExpansionModel<T> supplyChannel();

    static InventoryEntryExpansionModel<InventoryEntry> of() {
        return new InventoryEntryExpansionModelImpl<>();
    }
}
