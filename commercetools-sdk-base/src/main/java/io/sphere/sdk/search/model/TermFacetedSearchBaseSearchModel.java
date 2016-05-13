package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.TermFacetedSearchExpression;
import io.sphere.sdk.search.TermFacetExpression;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Model to build term facets and filters.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 */
abstract class TermFacetedSearchBaseSearchModel<T> extends Base implements FacetedSearchSearchModel<T> {
    protected static final TypeSerializer<String> TYPE_SERIALIZER = TypeSerializer.ofString();
    protected final SearchModel<T> searchModel;
    private final TermFacetExpression<T> facetExpression;
    private final TermFilterSearchModel<T, String> filterSearchModel;

    TermFacetedSearchBaseSearchModel(final SearchModel<T> searchModel) {
        this.searchModel = searchModel;
        this.facetExpression = new TermFacetSearchModelImpl<>(searchModel, TYPE_SERIALIZER).allTerms();
        this.filterSearchModel = new TermFilterSearchModelImpl<>(searchModel, TYPE_SERIALIZER);
    }

    @Override
    public SearchModel<T> getSearchModel() {
        return searchModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> allTerms() {
        return buildExpression(emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> is(final String value) {
        return containsAny(singletonList(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> containsAny(final Iterable<String> values) {
        return buildExpression(filterSearchModel.containsAnyAsString(values));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> containsAll(final Iterable<String> values) {
        return buildExpression(filterSearchModel.containsAll(values));
    }

    private TermFacetedSearchExpression<T> buildExpression(final List<FilterExpression<T>> filterExpressions) {
        return TermFacetedSearchExpression.of(facetExpression, filterExpressions);
    }
}
