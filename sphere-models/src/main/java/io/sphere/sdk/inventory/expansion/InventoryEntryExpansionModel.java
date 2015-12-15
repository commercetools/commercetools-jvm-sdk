package io.sphere.sdk.inventory.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;
import io.sphere.sdk.inventory.InventoryEntry;

public class InventoryEntryExpansionModel<T> extends ExpansionModel<T> {

    InventoryEntryExpansionModel() {
    }

    public ReferenceExpansionSupport<T> supplyChannel() {
        return expansionPath("supplyChannel");
    }

    public static InventoryEntryExpansionModel<InventoryEntry> of() {
        return new InventoryEntryExpansionModel<>();
    }
}
