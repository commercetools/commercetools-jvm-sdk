package io.sphere.sdk.search.model;

import io.sphere.sdk.search.SortExpression;

/**
 * A sort model to decide the direction of a model with just one value per resource
 * @see SingleValueSortSearchModelFactory to instantiate this class
 */
public final class SingleValueSortSearchModel<T> extends SortSearchModelImpl<T> {

    SingleValueSortSearchModel(final SearchModel<T> searchModel) {
        super(searchModel);
    }

    @Override
    public SortExpression<T> asc() {
        return super.asc();
    }

    @Override
    public SortExpression<T> desc() {
        return super.desc();
    }

    /**
     * Creates an instance of the search model to generate single-valued sort expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param <T> type of the resource
     * @return new instance of SingleValueSortSearchModel
     */
    public static <T> SingleValueSortSearchModel<T> of(final String attributePath) {
        return new SingleValueSortSearchModel<>(new SearchModelImpl<>(attributePath));
    }
}
