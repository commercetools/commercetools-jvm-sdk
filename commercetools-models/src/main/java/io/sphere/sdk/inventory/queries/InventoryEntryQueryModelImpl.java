package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;

final class InventoryEntryQueryModelImpl extends CustomResourceQueryModelImpl<InventoryEntry> implements InventoryEntryQueryModel {
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

    @Override
    public CustomQueryModel<InventoryEntry> custom() {
        return super.custom();
    }


}
