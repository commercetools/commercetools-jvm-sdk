package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class ReferenceSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> {

    public ReferenceSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T, S> id() {
        return new StringSearchModel<>(this, "id");
    }
}
