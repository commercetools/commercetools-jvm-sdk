package io.sphere.sdk.search;

/**
 * A sort model to decide the direction.
 */
public interface DirectionlessSearchSortModel<T> {

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
