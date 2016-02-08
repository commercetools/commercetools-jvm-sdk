package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class ReferenceFilterSearchModel<T> extends SearchModelImpl<T> {

    ReferenceFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> id() {
        return new StringSearchModel<>(this, "id").filtered();
    }
}
