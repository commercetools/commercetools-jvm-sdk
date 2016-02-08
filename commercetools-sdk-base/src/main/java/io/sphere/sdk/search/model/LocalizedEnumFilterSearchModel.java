package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class LocalizedEnumFilterSearchModel<T> extends SearchModelImpl<T> {

    LocalizedEnumFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> key() {
        return new StringSearchModel<>(this, "key").filtered();
    }

    public LocalizedStringFilterSearchModel<T> label() {
        return new LocalizedStringFilterSearchModel<>(this, "label");
    }
}
