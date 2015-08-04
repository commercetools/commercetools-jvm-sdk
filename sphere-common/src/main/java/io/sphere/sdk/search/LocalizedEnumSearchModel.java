package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class LocalizedEnumSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> {

    public LocalizedEnumSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T, S> key() {
        return new StringSearchModel<>(this, "key");
    }

    public LocalizedStringSearchModel<T, S> label() {
        return new LocalizedStringSearchModel<>(this, "label");
    }
}
