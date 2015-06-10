package io.sphere.sdk.inventories.expansion;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class InventoryExpansionModel<T> extends ExpansionModel<T> {
    public InventoryExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    InventoryExpansionModel() {
        super();
    }

    public static InventoryExpansionModel<InventoryEntry> of() {
        return new InventoryExpansionModel<>();
    }
}
