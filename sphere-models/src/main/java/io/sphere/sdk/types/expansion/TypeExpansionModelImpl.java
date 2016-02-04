package io.sphere.sdk.types.expansion;

import io.sphere.sdk.expansion.ExpansionModel;

final class TypeExpansionModelImpl<T> extends ExpansionModel<T> implements TypeExpansionModel<T> {
    TypeExpansionModelImpl(final String parentPath, final String path) {
        super(parentPath, path);
    }

    TypeExpansionModelImpl() {
        super();
    }
}
