package io.sphere.sdk.search.model;

import io.sphere.sdk.search.SearchSortDirection;
import io.sphere.sdk.search.SortExpression;

/**
 * A sort model to decide the direction.
 */
public interface SortSearchModel<T> {

    SortExpression<T> by(final SearchSortDirection direction);

    /**
     * @return the ascending sort direction
     */
    SortExpression<T> byAsc();

    /**
     * @return the descending sort direction
     */
    SortExpression<T> byDesc();
}
