package io.sphere.sdk.stores.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.List;

class StoreExpansionModelImpl<T> extends ExpansionModelImpl<T> implements StoreExpansionModel<T> {

    StoreExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    StoreExpansionModelImpl() {
        super();
    }

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return buildExpansionPaths();
    }
    
}
