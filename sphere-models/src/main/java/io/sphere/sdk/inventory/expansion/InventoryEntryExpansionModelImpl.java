package io.sphere.sdk.inventory.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.inventory.InventoryEntry;

final class InventoryEntryExpansionModelImpl<T> extends ExpansionModel<T> implements InventoryEntryExpansionModel<T> {

    InventoryEntryExpansionModelImpl() {
    }

    @Override
    public ChannelExpansionModel<T> supplyChannel() {
        return ChannelExpansionModel.of(buildPathExpression(), "supplyChannel");
    }
}
