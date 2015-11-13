package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class LocalizedEnumFacetAndFilterSearchModel<T> extends SearchModelImpl<T> {

    LocalizedEnumFacetAndFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetAndFilterSearchModel<T> key() {
        return new StringSearchModel<>(this, "key").facetedAndFiltered();
    }

    public LocalizedStringFacetAndFilterSearchModel<T> label() {
        return new LocalizedStringFacetAndFilterSearchModel<>(this, "label");
    }
}
