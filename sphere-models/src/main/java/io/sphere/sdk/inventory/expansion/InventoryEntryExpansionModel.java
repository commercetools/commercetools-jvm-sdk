package io.sphere.sdk.inventory.expansion;

import io.sphere.sdk.expansion.ExpansionPathsHolder;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.expansion.ExpansionModel;

public class InventoryEntryExpansionModel<T> extends ExpansionModel<T> {

    InventoryEntryExpansionModel() {
    }

    public ExpansionPathsHolder<T> supplyChannel() {
        return expansionPath("supplyChannel");
    }

    public static InventoryEntryExpansionModel<InventoryEntry> of() {
        return new InventoryEntryExpansionModel<>();
    }
}
