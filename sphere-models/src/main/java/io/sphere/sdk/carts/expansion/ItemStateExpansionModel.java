package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.Optional;

public class ItemStateExpansionModel<T> extends ExpansionModel<T> {
    ItemStateExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    public ExpansionPath<T> state() {
        return expansionPath("state");
    }
}
