package io.sphere.sdk.productselections.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.productselections.ProductSelection;

import java.util.List;

public interface ProductSelectionExpansionModel<T> extends ExpansionPathContainer<T> {
    ExpansionPathContainer<T> references();

    static ProductSelectionExpansionModel<ProductSelection> of() {
        return new ProductSelectionExpansionModelImpl<>();
    }

    static <T> ProductSelectionExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ProductSelectionExpansionModelImpl<>(parentPath, path);
    }
}
