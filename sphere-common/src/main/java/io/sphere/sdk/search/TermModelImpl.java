package io.sphere.sdk.search;

import javax.annotation.Nullable;

abstract class TermModelImpl<T, V> extends SearchModelImpl<T> implements TermModel<T, V> {

    TermModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public UntypedSearchModel<T> untyped() {
        return new UntypedSearchModel<>(this, null);
    }
}