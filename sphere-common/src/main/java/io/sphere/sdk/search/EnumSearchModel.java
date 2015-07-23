package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class EnumSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> {

    public EnumSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T, S> key() {
        return new StringSearchModel<>(this, "key");
    }

    public StringSearchModel<T, S> label() {
        return new StringSearchModel<>(this, "label");
    }
}
