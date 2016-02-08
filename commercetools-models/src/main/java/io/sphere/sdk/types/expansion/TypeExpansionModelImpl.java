package io.sphere.sdk.types.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

final class TypeExpansionModelImpl<T> extends ExpansionModelImpl<T> implements TypeExpansionModel<T> {
    TypeExpansionModelImpl(final String parentPath, final String path) {
        super(parentPath, path);
    }

    TypeExpansionModelImpl() {
        super();
    }
}
