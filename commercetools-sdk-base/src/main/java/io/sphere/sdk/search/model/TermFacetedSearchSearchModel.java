package io.sphere.sdk.search.model;

import io.sphere.sdk.search.TermFacetedSearchExpression;

/**
 * Model to build term facets and filters.
 * @param <T> type of the resource
 */
public final class TermFacetedSearchSearchModel<T> extends TermFacetedSearchBaseSearchModel<T> {

    TermFacetedSearchSearchModel(final SearchModel<T> searchModel) {
        super(searchModel);
    }

    @Override
    public TermFacetedSearchExpression<T> allTerms() {
        return super.allTerms();
    }

    @Override
    public TermFacetedSearchExpression<T> is(final String value) {
        return super.is(value);
    }

    @Override
    public TermFacetedSearchExpression<T> containsAny(final Iterable<String> values) {
        return super.containsAny(values);
    }

    @Override
    public TermFacetedSearchExpression<T> containsAll(final Iterable<String> values) {
        return super.containsAll(values);
    }

    /**
     * Creates an instance of the search model to generate term faceted search expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param <T> type of the resource
     * @return new instance of TermFacetAndFilterSearchModel
     */
    public static <T> TermFacetedSearchSearchModel<T> of(final String attributePath) {
        return new TermFacetedSearchSearchModel<>(new SearchModelImpl<>(attributePath));
    }
}
