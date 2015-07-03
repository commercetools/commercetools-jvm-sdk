package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class InventoryEntryQueryModel extends DefaultModelQueryModelImpl<InventoryEntry> {
    public InventoryEntryQueryModel(final Optional<? extends QueryModel<InventoryEntry>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static InventoryEntryQueryModel of() {
        return new InventoryEntryQueryModel(Optional.empty(), Optional.empty());
    }

    public StringQuerySortingModel<InventoryEntry> sku() {
        return stringModel("sku");
    }

    public ReferenceQueryModel<InventoryEntry, Channel> supplyChannel() {
        return referenceModel("supplyChannel");
    }

    public LongQuerySortingModel<InventoryEntry> quantityOnStock() {
        return longModel("quantityOnStock");
    }

    public LongQuerySortingModel<InventoryEntry> availableQuantity() {
        return longModel("availableQuantity");
    }

    public IntegerQuerySortingModel<InventoryEntry> restockableInDays() {
        return integerModel("restockableInDays");
    }
}
