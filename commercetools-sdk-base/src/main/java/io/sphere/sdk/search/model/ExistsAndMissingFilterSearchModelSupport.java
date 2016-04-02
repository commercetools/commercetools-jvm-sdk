package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

public interface ExistsAndMissingFilterSearchModelSupport<T> extends MissingFilterSearchModelSupport<T>, ExistsFilterSearchModelSupport<T> {
    @Override
    List<FilterExpression<T>> exists();

    @Override
    List<FilterExpression<T>> missing();
}
