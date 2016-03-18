package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

public interface ExistsFilterSearchModelSupport<T> {
    /**
     * Creates filters for a field that contains at least one value.
     *
     * {@include.example io.sphere.sdk.products.search.ExistsAndMissingFilterIntegrationTest#categoriesExists()}
     *
     * @return filters
     */
    List<FilterExpression<T>> exists();
}
