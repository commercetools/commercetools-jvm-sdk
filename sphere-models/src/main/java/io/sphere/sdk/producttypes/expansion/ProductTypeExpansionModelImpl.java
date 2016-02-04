package io.sphere.sdk.producttypes.expansion;

import io.sphere.sdk.expansion.ExpansionModel;

import java.util.List;

final class ProductTypeExpansionModelImpl<T> extends ExpansionModel<T> implements ProductTypeExpansionModel<T> {
    ProductTypeExpansionModelImpl() {
    }

    ProductTypeExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }
}
