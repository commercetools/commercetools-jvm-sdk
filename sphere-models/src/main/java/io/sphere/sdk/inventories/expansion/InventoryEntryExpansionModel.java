package io.sphere.sdk.inventories.expansion;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

public class InventoryEntryExpansionModel<T> extends ExpansionModel<T> {

    InventoryEntryExpansionModel() {
    }

    public ExpansionPath<T> supplyChannel() {
        return ExpansionPath.of("supplyChannel");
    }

    public static InventoryEntryExpansionModel<InventoryEntry> of() {
        return new InventoryEntryExpansionModel<>();
    }
}
