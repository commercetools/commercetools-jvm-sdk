package io.sphere.sdk.search;

import javax.annotation.Nullable;

abstract class RangeTermModelImpl<T, V extends Comparable<? super V>> extends SearchModelImpl<T> implements RangeTermModel<T, V> {

    RangeTermModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public UntypedSearchModel<T> untyped() {
        return new UntypedSearchModel<>(this, null);
    }
}