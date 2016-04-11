package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.SearchSortDirection;
import io.sphere.sdk.search.SortExpression;

import static io.sphere.sdk.search.SearchSortDirection.ASC;
import static io.sphere.sdk.search.SearchSortDirection.DESC;

/**
 * A sort model to decide the direction of a model with single values.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 */
abstract class SortSearchModelImpl<T> extends Base implements SortSearchModel<T> {
    private final SearchModel<T> searchModel;

    SortSearchModelImpl(final SearchModel<T> searchModel) {
        this.searchModel = searchModel;
    }

    @Override
    public SearchModel<T> getSearchModel() {
        return searchModel;
    }

    public SortExpression<T> by(final SearchSortDirection direction) {
        return new SortExpressionImpl<>(searchModel, direction);
    }

    /**
     * @return the ascending sort direction
     */
    public SortExpression<T> asc() {
        return by(ASC);
    }

    /**
     * @return the descending sort direction
     */
    public SortExpression<T> desc() {
        return by(DESC);
    }
}
