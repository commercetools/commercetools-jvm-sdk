package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class InventoryEntryQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    public InventoryEntryQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static InventoryEntryQueryModel<InventoryEntry> of() {
        return new InventoryEntryQueryModel<>(Optional.empty(), Optional.empty());
    }

    public StringQuerySortingModel<T> sku() {
        return new StringQuerySortingModel<>(Optional.of(this), "sku");
    }

    public ReferenceQueryModel<T, Channel> supplyChannel() {
        return new ReferenceQueryModel<>(Optional.of(this), "supplyChannel");
    }

    public LongQuerySortingModel<T> quantityOnStock() {
        return new LongQuerySortingModel<>(Optional.of(this), "quantityOnStock");
    }

    public LongQuerySortingModel<T> availableQuantity() {
        return new LongQuerySortingModel<>(Optional.of(this), "availableQuantity");
    }

//TODO on March 11, 2015 this does not yet work in the backend
//    public IntegerQuerySortingModel<InventoryEntry> restockableInDays() {
//        return new IntegerQuerySortingModel<>(Optional.of(this), "restockableInDays");
//    }
}
