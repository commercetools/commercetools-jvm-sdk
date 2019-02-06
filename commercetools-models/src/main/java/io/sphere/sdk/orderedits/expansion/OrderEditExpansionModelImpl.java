package io.sphere.sdk.orderedits.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.List;

final class OrderEditExpansionModelImpl<T> extends ExpansionModelImpl<T> implements OrderEditExpansionModel<T> {

    OrderEditExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    OrderEditExpansionModelImpl() {
        super();
    }

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return buildExpansionPaths();
    }
}
