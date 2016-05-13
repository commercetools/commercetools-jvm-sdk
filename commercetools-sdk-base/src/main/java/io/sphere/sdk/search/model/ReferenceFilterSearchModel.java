package io.sphere.sdk.search.model;

public interface ReferenceFilterSearchModel<T> {
    TermFilterSearchModel<T, String> id();
}
