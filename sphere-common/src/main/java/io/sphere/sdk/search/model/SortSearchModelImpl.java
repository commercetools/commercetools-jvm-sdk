package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.SearchSortDirection;
import io.sphere.sdk.search.SortExpression;

import static io.sphere.sdk.search.SearchSortDirection.ASC;
import static io.sphere.sdk.search.SearchSortDirection.DESC;

public class SortSearchModelImpl<T, S extends SortSearchModel<T>> extends Base implements SortSearchModel<T> {
    private final SearchModel<T> searchModel;

    SortSearchModelImpl(final SortableSearchModel<T, S> searchModel) {
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
