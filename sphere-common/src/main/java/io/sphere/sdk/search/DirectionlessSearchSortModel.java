package io.sphere.sdk.search;

/**
 * A sort model to decide the direction.
 */
public interface DirectionlessSearchSortModel<T> {

    SearchSort<T> by(final SearchSortDirection direction);

    /**
     * @return the ascending sort direction
     */
    SearchSort<T> byAsc();

    /**
     * @return the descending sort direction
     */
    SearchSort<T> byDesc();
}
