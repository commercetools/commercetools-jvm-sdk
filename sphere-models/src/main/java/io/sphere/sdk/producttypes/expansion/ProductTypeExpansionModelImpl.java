package io.sphere.sdk.producttypes.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class ProductTypeExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ProductTypeExpansionModel<T> {
    ProductTypeExpansionModelImpl() {
    }

    ProductTypeExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }
}
