package io.sphere.sdk.search;

import java.util.List;

public interface FilterSupport<T> {

    List<FilterExpression<T>> resultFilters();

    List<FilterExpression<T>> queryFilters();

    List<FilterExpression<T>> facetFilters();
}
