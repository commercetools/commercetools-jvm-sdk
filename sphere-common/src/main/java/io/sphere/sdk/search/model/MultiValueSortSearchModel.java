package io.sphere.sdk.search.model;

import io.sphere.sdk.search.SortExpression;

import static io.sphere.sdk.search.SearchSortDirection.*;

/**
 * A sort model to decide the direction of a model with multiple values per entity.
 * @see MultiValueSortSearchModelFactory to instantiate this class
 */
public class MultiValueSortSearchModel<T> extends SortSearchModelImpl<T, MultiValueSortSearchModel<T>> {

    MultiValueSortSearchModel(final SortableSearchModel<T, MultiValueSortSearchModel<T>> searchModel) {
        super(searchModel);
    }

    /**
     * When the sort direction is ascending, the minimum value is used.
     * @return the ascending sort direction
     */
    @Override
    public SortExpression<T> byAsc() {
        return super.byAsc();
    }

    /**
     * When the direction is descending, the maximum value is used.
     * @return the descending sort direction
     */
    @Override
    public SortExpression<T> byDesc() {
        return super.byDesc();
    }

    /**
     * Changes the default behaviour of the ascending sort by using the maximum value instead.
     * @return the ascending sort direction using the maximum value
     */
    public SortExpression<T> byAscWithMax() {
        return by(ASC_MAX);
    }

    /**
     * Changes the default behaviour of the descending sort by using the minimum value instead.
     * @return the descending sort direction using the minimum value
     */
    public SortExpression<T> byDescWithMin() {
        return by(DESC_MIN);
    }
}
