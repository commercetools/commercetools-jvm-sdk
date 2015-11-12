package io.sphere.sdk.search.model;

import io.sphere.sdk.search.SortExpression;

/**
 * A sort model to decide the direction of a model with just one value per entity
 * @see SingleValueSortSearchModelFactory to instantiate this class
 */
public class SingleValueSortSearchModel<T> extends SortSearchModelImpl<T> {

    SingleValueSortSearchModel(final SearchModel<T> searchModel) {
        super(searchModel);
    }

    @Override
    public SortExpression<T> byAsc() {
        return super.byAsc();
    }

    @Override
    public SortExpression<T> byDesc() {
        return super.byDesc();
    }

    public static <T> SingleValueSortSearchModel<T> of(final String path) {
        return new SingleValueSortSearchModel<>(new SearchModelImpl<>(path));
    }
}
