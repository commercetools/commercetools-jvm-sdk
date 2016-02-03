package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class EnumFacetSearchModel<T> extends SearchModelImpl<T> {

    EnumFacetSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetSearchModel<T, String> key() {
        return new StringSearchModel<>(this, "key").faceted();
    }

    public TermFacetSearchModel<T, String> label() {
        return new StringSearchModel<>(this, "label").faceted();
    }
}
