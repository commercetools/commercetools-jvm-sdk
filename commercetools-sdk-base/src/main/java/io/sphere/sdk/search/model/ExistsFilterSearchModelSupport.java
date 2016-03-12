package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

public interface ExistsFilterSearchModelSupport<T> {
    List<FilterExpression<T>> exists();
}
