package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.TermFacetAndFilterExpression;
import io.sphere.sdk.search.TermFacetExpression;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Model to build term facets and filters.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 */
abstract class TermFacetAndFilterBaseSearchModel<T> extends Base implements FacetAndFilterSearchModel<T> {
    protected static final TypeSerializer<String> TYPE_SERIALIZER = TypeSerializer.ofString();
    protected final SearchModel<T> searchModel;
    private final TermFacetExpression<T> facetExpression;
    private final TermFilterSearchModel<T, String> filterSearchModel;

    TermFacetAndFilterBaseSearchModel(final SearchModel<T> searchModel) {
        this.searchModel = searchModel;
        this.facetExpression = new TermFacetSearchModel<>(searchModel, TYPE_SERIALIZER).allTerms();
        this.filterSearchModel = new TermFilterSearchModel<>(searchModel, TYPE_SERIALIZER);
    }

    @Override
    public TermFacetAndFilterExpression<T> allTerms() {
        return buildExpression(emptyList());
    }

    @Override
    public TermFacetAndFilterExpression<T> by(final String value) {
        return byAny(singletonList(value));
    }

    @Override
    public TermFacetAndFilterExpression<T> byAny(final Iterable<String> values) {
        return buildExpression(filterSearchModel.byAnyAsString(values));
    }

    @Override
    public TermFacetAndFilterExpression<T> byAll(final Iterable<String> values) {
        return buildExpression(filterSearchModel.byAll(values));
    }

    private TermFacetAndFilterExpression<T> buildExpression(final List<FilterExpression<T>> filterExpressions) {
        return TermFacetAndFilterExpression.of(facetExpression, filterExpressions);
    }
}
