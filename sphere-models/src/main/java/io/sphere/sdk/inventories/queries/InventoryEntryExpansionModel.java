package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

public class InventoryEntryExpansionModel<T> extends ExpansionModel<T> {

    InventoryEntryExpansionModel() {
    }

    ExpansionPath<T> supplyChannel() {
        return ExpansionPath.of("supplyChannel");
    }
}
