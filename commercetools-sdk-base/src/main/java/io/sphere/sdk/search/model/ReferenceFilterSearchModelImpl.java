package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

final class ReferenceFilterSearchModelImpl<T> extends SearchModelImpl<T> implements ReferenceFilterSearchModel<T> {

    protected ReferenceFilterSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> id() {
        return new StringSearchModel<>(this, "id").filtered();
    }
}
