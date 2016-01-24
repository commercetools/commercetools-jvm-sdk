package io.sphere.sdk.search.model;

import io.sphere.sdk.search.TermFacetAndFilterExpression;

/**
 * Model to build term facets and filters.
 * @param <T> type of the resource
 */
public class TermFacetAndFilterSearchModel<T> extends TermFacetAndFilterBaseSearchModel<T> {

    TermFacetAndFilterSearchModel(final SearchModel<T> searchModel) {
        super(searchModel);
    }

    @Override
    public TermFacetAndFilterExpression<T> allTerms() {
        return super.allTerms();
    }

    @Override
    public TermFacetAndFilterExpression<T> is(final String value) {
        return super.is(value);
    }

    @Override
    public TermFacetAndFilterExpression<T> containsAny(final Iterable<String> values) {
        return super.containsAny(values);
    }

    @Override
    public TermFacetAndFilterExpression<T> containsAll(final Iterable<String> values) {
        return super.containsAll(values);
    }

    /**
     * Creates an instance of the search model to generate term faceted search expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param <T> type of the resource
     * @return new instance of TermFacetAndFilterSearchModel
     */
    public static <T> TermFacetAndFilterSearchModel<T> of(final String attributePath) {
        return new TermFacetAndFilterSearchModel<>(new SearchModelImpl<>(attributePath));
    }
}
