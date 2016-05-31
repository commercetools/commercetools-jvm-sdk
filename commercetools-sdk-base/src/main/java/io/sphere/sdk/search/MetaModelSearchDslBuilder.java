package io.sphere.sdk.search;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStringEntry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;

/**
 *
 * @param <T> type of the search result
 * @param <C> type of the class implementing this class
 * @param <S> type of the search model
 * @param <F> type of the facet model
 * @param <E> type of the expansion model
 */
public final class MetaModelSearchDslBuilder<T, C extends MetaModelSearchDsl<T, C, S, L, F, E>, S, L, F, E> extends Base implements Builder<C> {

    @Nullable
    protected LocalizedStringEntry text;
    @Nullable
    protected Boolean fuzzy;
    @Nullable
    protected Integer fuzzyLevel;
    protected List<FacetExpression<T>> facets = emptyList();
    protected List<FilterExpression<T>> resultFilters = emptyList();
    protected List<FilterExpression<T>> queryFilters = emptyList();
    protected List<FilterExpression<T>> facetFilters = emptyList();
    protected List<FacetedSearchExpression<T>> facetedSearch = emptyList();
    protected List<SortExpression<T>> sort = emptyList();
    @Nullable
    protected Long limit;
    @Nullable
    protected Long offset;
    protected List<ExpansionPath<T>> expansionPaths = emptyList();
    protected List<NameValuePair> additionalQueryParameters = emptyList();
    protected final String endpoint;
    protected final Function<HttpResponse, PagedSearchResult<T>> resultMapper;
    protected final S sortModel;
    protected final L filterModel;
    protected final F facetModel;
    protected final E expansionModel;
    protected final Function<MetaModelSearchDslBuilder<T, C, S, L, F, E>, C> searchDslBuilderFunction;

    public MetaModelSearchDslBuilder(final String endpoint, final Function<HttpResponse, PagedSearchResult<T>> resultMapper,
                                     final S sortModel, final L filterModel, final F facetModel, final E expansionModel,
                                     final Function<MetaModelSearchDslBuilder<T, C, S, L, F, E>, C> searchDslBuilderFunction) {
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
        this.sortModel = sortModel;
        this.filterModel = filterModel;
        this.facetModel = facetModel;
        this.expansionModel = expansionModel;
        this.searchDslBuilderFunction = searchDslBuilderFunction;
    }

    public MetaModelSearchDslBuilder(final MetaModelSearchDslImpl<T, C, S, L, F, E> template) {
        this(template.endpoint(), r -> template.deserialize(r), template.getSortModel(), template.getFilterModel(),
                template.getFacetModel(), template.getExpansionModel(), template.searchDslBuilderFunction);
        text = template.text();
        facets = template.facets();
        resultFilters = template.resultFilters();
        queryFilters = template.queryFilters();
        facetFilters = template.facetFilters();
        facetedSearch = template.facetedSearch();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
        expansionPaths = template.expansionPaths();
        additionalQueryParameters = template.additionalQueryParameters();
        fuzzy = template.isFuzzy();
        fuzzyLevel = template.fuzzyLevel();
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> text(@Nullable final LocalizedStringEntry text) {
        this.text = text;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> facets(final List<FacetExpression<T>> facets) {
        this.facets = facets;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> resultFilters(final List<FilterExpression<T>> resultFilters) {
        this.resultFilters = resultFilters;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> queryFilters(final List<FilterExpression<T>> queryFilters) {
        this.queryFilters = queryFilters;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> facetFilters(final List<FilterExpression<T>> facetFilters) {
        this.facetFilters = facetFilters;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> facetedSearch(final List<FacetedSearchExpression<T>> facetedSearch) {
        this.facetedSearch = facetedSearch;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> sort(final List<SortExpression<T>> sort) {
        this.sort = sort;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> limit(@Nullable final Long limit) {
        this.limit = limit;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> offset(@Nullable final Long offset) {
        this.offset = offset;
        return this;
    }


    public MetaModelSearchDslBuilder<T, C, S, L, F, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> additionalQueryParameters(final List<NameValuePair> additionalQueryParameters) {
        this.additionalQueryParameters = additionalQueryParameters;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> fuzzy(final Boolean fuzzy) {
        this.fuzzy = fuzzy;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, L, F, E> fuzzyLevel(final Integer fuzzyLevel) {
        this.fuzzyLevel = fuzzyLevel;
        return this;
    }

    @Override
    public C build() {
        return searchDslBuilderFunction.apply(this);
    }
}
