package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

public interface ReferenceFilterSearchModel<T> extends ExistsAndMissingFilterSearchModelSupport<T> {
    TermFilterSearchModel<T, String> id();

    @Override
    List<FilterExpression<T>> exists();

    @Override
    List<FilterExpression<T>> missing();
}
