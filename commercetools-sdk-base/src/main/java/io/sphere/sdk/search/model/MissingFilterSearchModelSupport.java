package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

public interface MissingFilterSearchModelSupport<T> {
    /**
     * Creates filters for fields that contain no values.
     *
     * {@include.example io.sphere.sdk.products.search.ExistsAndMissingFilterIntegrationTest#categoriesMissing()}
     *
     * @return filters
     */
    List<FilterExpression<T>> missing();
}
