package io.sphere.sdk.search.model;

import io.sphere.sdk.search.SearchSortDirection;
import io.sphere.sdk.search.SortExpression;

/**
 * A sort model to decide the direction.
 */
public interface SortSearchModel<T> {

    /**
     * The search model for the facet.
     * @return the facet search model
     */
    SearchModel<T> getSearchModel();

    SortExpression<T> by(final SearchSortDirection direction);

    /**
     * @return the ascending sort direction
     */
    SortExpression<T> asc();

    /**
     * @return the descending sort direction
     */
    SortExpression<T> desc();

    /**
     * @deprecated use {@link #asc()} instead
     * @return deprecated
     */
    @Deprecated
    default SortExpression<T> byAsc() {
        return asc();
    }

    /**
     * @deprecated use {@link #desc()} instead
     * @return deprecated
     */
    @Deprecated
    default SortExpression<T> byDesc() {
        return desc();
    }
}
