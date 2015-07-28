package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.queries.*;

public class InventoryEntryQueryModel extends DefaultModelQueryModelImpl<InventoryEntry> {
    public InventoryEntryQueryModel(final QueryModel<InventoryEntry> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static InventoryEntryQueryModel of() {
        return new InventoryEntryQueryModel(null, null);
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
