package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.queries.*;

final class InventoryEntryQueryModelImpl extends ResourceQueryModelImpl<InventoryEntry> implements InventoryEntryQueryModel {
    private InventoryEntryQueryModelImpl(final QueryModel<InventoryEntry> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static InventoryEntryQueryModel of() {
        return new InventoryEntryQueryModelImpl(null, null);
    }

    @Override
    public StringQuerySortingModel<InventoryEntry> sku() {
        return stringModel("sku");
    }

    @Override
    public ReferenceQueryModel<InventoryEntry, Channel> supplyChannel() {
        return referenceModel("supplyChannel");
    }

    @Override
    public LongQuerySortingModel<InventoryEntry> quantityOnStock() {
        return longModel("quantityOnStock");
    }

    @Override
    public LongQuerySortingModel<InventoryEntry> availableQuantity() {
        return longModel("availableQuantity");
    }

    @Override
    public IntegerQuerySortingModel<InventoryEntry> restockableInDays() {
        return integerModel("restockableInDays");
    }
}
