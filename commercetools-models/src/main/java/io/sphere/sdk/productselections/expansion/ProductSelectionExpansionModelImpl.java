package io.sphere.sdk.productselections.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

final class ProductSelectionExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ProductSelectionExpansionModel<T> {
    ProductSelectionExpansionModelImpl() {
    }

    ProductSelectionExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public ExpansionPathContainer<T> references() {
        return expansionPath("references[*]");
    }
}
