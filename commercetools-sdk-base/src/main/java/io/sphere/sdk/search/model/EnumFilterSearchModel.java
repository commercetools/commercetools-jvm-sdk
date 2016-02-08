package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class EnumFilterSearchModel<T> extends SearchModelImpl<T> {

    EnumFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> key() {
        return new StringSearchModel<>(this, "key").filtered();
    }

    public TermFilterSearchModel<T, String> label() {
        return new StringSearchModel<>(this, "label").filtered();
    }
}
