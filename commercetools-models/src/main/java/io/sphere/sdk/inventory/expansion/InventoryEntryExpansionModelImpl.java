package io.sphere.sdk.inventory.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;

final class InventoryEntryExpansionModelImpl<T> extends ExpansionModelImpl<T> implements InventoryEntryExpansionModel<T> {

    InventoryEntryExpansionModelImpl() {
    }

    @Override
    public ChannelExpansionModel<T> supplyChannel() {
        return ChannelExpansionModel.of(buildPathExpression(), "supplyChannel");
    }
}
