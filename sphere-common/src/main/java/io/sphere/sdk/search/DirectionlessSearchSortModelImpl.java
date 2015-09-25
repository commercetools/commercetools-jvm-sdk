package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.search.SearchSortDirection.*;

/**
 * A sort model to decide the direction.
 */
public class DirectionlessSearchSortModelImpl<T> extends Base implements DirectionlessSearchSortModel<T> {
    private final SearchModel<T> searchModel;

    public DirectionlessSearchSortModelImpl(final SearchModel<T> searchModel) {
        this.searchModel = searchModel;
    }

    public SortExpression<T> by(final SearchSortDirection direction) {
        return new SortExpressionImpl<>(searchModel, direction);
    }

    /**
     * @return the ascending sort direction
     */
    public SortExpression<T> byAsc() {
        return by(ASC);
    }

    /**
     * @return the descending sort direction
     */
    public SortExpression<T> byDesc() {
        return by(DESC);
    }
}
