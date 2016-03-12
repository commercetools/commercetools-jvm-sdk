package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

public interface MissingFilterSearchModelSupport<T> {
    List<FilterExpression<T>> missing();
}
