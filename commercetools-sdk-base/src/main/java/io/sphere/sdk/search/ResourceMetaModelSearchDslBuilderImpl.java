package io.sphere.sdk.search;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class ResourceMetaModelSearchDslBuilderImpl<B, T, C extends MetaModelSearchDsl<T, C, S, L, F, E>, S, L, F, E> extends Base implements ResourceMetaModelSearchDslBuilder<B, T, C, S, L, F, E> {
    private C delegate;

    protected ResourceMetaModelSearchDslBuilderImpl(final C delegate) {
        this.delegate = delegate;
    }

    protected abstract B getThis();

    private B op(final UnaryOperator<C> op) {
        this.delegate = op.apply(this.delegate);
        return getThis();
    }

    @Override
    public C build() {
        return delegate;
    }


    @Override
    public B facetedSearch(final FacetedSearchExpression<T> facetedSearchExpression) {
        return op(d -> d.withFacetedSearch(facetedSearchExpression));
    }

    @Override
    public B text(final LocalizedStringEntry text) {
        return op(d -> d.withText(text));
    }

    @Override
    public B text(final Locale locale, final String text) {
        return op(d -> d.withText(locale, text));
    }

    @Override
    public B fuzzy(final Boolean fuzzy) {
        return op(d -> d.withFuzzy(fuzzy));
    }

    @Override
    public B fuzzyLevel(final Integer fuzzyLevel) {
        return op(d -> d.withFuzzyLevel(fuzzyLevel));
    }

    @Override
    public B facets(final List<FacetExpression<T>> facets) {
        return op(d -> d.withFacets(facets));
    }

    @Override
    public B plusFacets(final Function<F, FacetExpression<T>> m) {
        return op(d -> d.plusFacets(m));
    }

    @Override
    public B plusResultFilters(final List<FilterExpression<T>> filterExpressions) {
        return op(d -> d.withResultFilters(filterExpressions));
    }

    @Override
    public B plusResultFilters(final Function<L, List<FilterExpression<T>>> m) {
        return op(d -> d.plusResultFilters(m));
    }

    @Override
    public B plusQueryFilters(final List<FilterExpression<T>> filterExpressions) {
        return op(d -> d.plusQueryFilters(filterExpressions));
    }

    @Override
    public B plusQueryFilters(final Function<L, List<FilterExpression<T>>> m) {
        return op(d -> d.plusQueryFilters(m));
    }

    @Override
    public B plusFacetFilters(final List<FilterExpression<T>> filterExpressions) {
        return op(d -> d.plusFacetFilters(filterExpressions));
    }

    @Override
    public B plusFacetFilters(final Function<L, List<FilterExpression<T>>> m) {
        return op(d -> d.plusFacetFilters(m));
    }

    @Override
    public B plusSort(final List<SortExpression<T>> sortExpressions) {
        return op(d -> d.plusSort(sortExpressions));
    }

    @Override
    public B plusSort(final SortExpression<T> sortExpression) {
        return op(d -> d.plusSort(sortExpression));
    }

    @Override
    public B plusSort(final Function<S, SortExpression<T>> m) {
        return op(d -> d.plusSort(m));
    }

    @Override
    public B limit(final Long limit) {
        return op(d -> d.withLimit(limit));
    }

    @Override
    public B limit(final long limit) {
        return op(d -> d.withLimit(limit));
    }

    @Override
    public B offset(final Long offset) {
        return op(d -> d.withOffset(offset));
    }

    @Override
    public B offset(final long offset) {
        return op(d -> d.withOffset(offset));
    }

    @Override
    public B plusExpansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return op(d -> d.plusExpansionPaths(expansionPaths));
    }

    @Override
    public B plusExpansionPaths(final ExpansionPath<T> expansionPath) {
        return op(d -> d.plusExpansionPaths(expansionPath));
    }

    @Override
    public B plusFacetedSearch(final FacetedSearchExpression<T> facetedSearchExpression) {
        return op(d -> d.plusFacetedSearch(facetedSearchExpression));
    }

    @Override
    public B plusFacetedSearch(final List<FacetedSearchExpression<T>> facetedSearchExpressions) {
        return op(d -> d.plusFacetedSearch(facetedSearchExpressions));
    }

    @Override
    public B facetedSearch(final List<FacetedSearchExpression<T>> facetedSearchExpressions) {
        return op(d -> d.withFacetedSearch(facetedSearchExpressions));
    }

    @Override
    public B queryFilters(final List<FilterExpression<T>> filterExpressions) {
        return op(d -> d.withQueryFilters(filterExpressions));
    }

    @Override
    public B resultFilters(final List<FilterExpression<T>> filterExpressions) {
        return op(d -> d.withResultFilters(filterExpressions));
    }

    @Override
    public B expansionPaths(final ExpansionPath<T> expansionPath) {
        return op(d -> d.withExpansionPaths(expansionPath));
    }

    @Override
    public B expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return op(d -> d.withExpansionPaths(expansionPaths));
    }

    @Override
    public B expansionPaths(final ExpansionPathContainer<T> holder) {
        return op(d -> d.withExpansionPaths(holder));
    }

    @Override
    public B expansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return op(d -> d.withExpansionPaths(m));
    }

    @Override
    public B facetFilters(final List<FilterExpression<T>> filterExpressions) {
        return op(d -> d.withFacetFilters(filterExpressions));
    }

    @Override
    public B facetFilters(final Function<L, List<FilterExpression<T>>> m) {
        return op(d -> d.withFacetFilters(m));
    }

    @Override
    public B facets(final FacetExpression<T> facetExpression) {
        return op(d -> d.withFacets(facetExpression));
    }

    @Override
    public B facets(final Function<F, FacetExpression<T>> m) {
        return op(d -> d.withFacets(m));
    }

    @Override
    public B plusExpansionPaths(final ExpansionPathContainer<T> holder) {
        return op(d -> d.plusExpansionPaths(holder));
    }

    @Override
    public B plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return op(d -> d.plusExpansionPaths(m));
    }

    @Override
    public B plusFacets(final FacetExpression<T> facetExpression) {
        return op(d -> d.plusFacets(facetExpression));
    }

    @Override
    public B plusFacets(final List<FacetExpression<T>> facetExpressions) {
        return op(d -> d.plusFacets(facetExpressions));
    }

    @Override
    public B queryFilters(final Function<L, List<FilterExpression<T>>> m) {
        return op(d -> d.withQueryFilters(m));
    }

    @Override
    public B resultFilters(final Function<L, List<FilterExpression<T>>> m) {
        return op(d -> d.withResultFilters(m));
    }

    @Override
    public B sort(final Function<S, SortExpression<T>> m) {
        return op(d -> d.withSort(m));
    }

    @Override
    public B sort(final SortExpression<T> sortExpression) {
        return op(d -> d.withSort(sortExpression));
    }

    @Override
    public B sort(final List<SortExpression<T>> sortExpressions) {
        return op(d -> d.withSort(sortExpressions));
    }
}
