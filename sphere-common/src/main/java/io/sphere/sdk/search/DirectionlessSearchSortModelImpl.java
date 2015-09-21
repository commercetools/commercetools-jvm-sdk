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

    public SearchSort<T> by(final SearchSortDirection direction) {
        return new SphereSearchSort<>(searchModel, direction);
    }

    /**
     * @return the ascending sort direction
     */
    public SearchSort<T> byAsc() {
        return by(ASC);
    }

    /**
     * @return the descending sort direction
     */
    public SearchSort<T> byDesc() {
        return by(DESC);
    }
}
