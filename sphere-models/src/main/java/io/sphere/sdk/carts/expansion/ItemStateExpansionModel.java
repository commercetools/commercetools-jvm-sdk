package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathsHolder;

import java.util.List;

public class ItemStateExpansionModel<T> extends ExpansionModel<T> {
    ItemStateExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPathsHolder<T> state() {
        return expansionPath("state");
    }
}
