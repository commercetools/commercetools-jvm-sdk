package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

public class ItemStateExpansionModel<T> extends ExpansionModel<T> {
    ItemStateExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPath<T> state() {
        return expansionPath("state");
    }
}
