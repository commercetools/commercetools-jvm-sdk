package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class LocalizedEnumFacetSearchModel<T> extends SearchModelImpl<T> {

    LocalizedEnumFacetSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetSearchModel<T, String> key() {
        return new StringSearchModel<>(this, "key").faceted();
    }

    public LocalizedStringFacetSearchModel<T> label() {
        return new LocalizedStringFacetSearchModel<>(this, "label");
    }
}
