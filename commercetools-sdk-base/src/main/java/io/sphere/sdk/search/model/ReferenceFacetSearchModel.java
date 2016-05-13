package io.sphere.sdk.search.model;

public interface ReferenceFacetSearchModel<T> extends SearchModel<T> {
    TermFacetSearchModel<T, String> id();
}
