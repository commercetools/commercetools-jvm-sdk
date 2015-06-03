package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class InventoryEntryQueryModel extends DefaultModelQueryModelImpl<InventoryEntry> {
    public InventoryEntryQueryModel(final Optional<? extends QueryModel<InventoryEntry>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static InventoryEntryQueryModel get() {
        return new InventoryEntryQueryModel(Optional.empty(), Optional.empty());
    }

    public StringQuerySortingModel<InventoryEntry> sku() {
        return new StringQuerySortingModel<>(Optional.of(this), "sku");
    }

    public ReferenceQueryModel<InventoryEntry, Channel> supplyChannel() {
        return new ReferenceQueryModel<>(Optional.of(this), "supplyChannel");
    }

    public LongQuerySortingModel<InventoryEntry> quantityOnStock() {
        return new LongQuerySortingModelImpl<>(Optional.of(this), "quantityOnStock");
    }

    public LongQuerySortingModel<InventoryEntry> availableQuantity() {
        return new LongQuerySortingModelImpl<>(Optional.of(this), "availableQuantity");
    }

//TODO on March 11, 2015 this does not yet work in the backend
//    public IntegerQuerySortingModel<InventoryEntry> restockableInDays() {
//        return new IntegerQuerySortingModel<>(Optional.of(this), "restockableInDays");
//    }
}
