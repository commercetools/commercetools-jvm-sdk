package io.sphere.sdk.search;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.http.HttpQueryParameter;
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
 * @param <E> type of the expansion model
 */
public class MetaModelSearchDslBuilder<T, C extends MetaModelSearchDsl<T, C, S, E>, S, E> extends Base implements Builder<C> {

    @Nullable
    protected LocalizedStringEntry text;
    protected List<FacetExpression<T>> facets = emptyList();
    protected List<FilterExpression<T>> resultFilters = emptyList();
    protected List<FilterExpression<T>> queryFilters = emptyList();
    protected List<FilterExpression<T>> facetFilters = emptyList();
    protected List<SortExpression<T>> sort = emptyList();
    @Nullable
    protected Long limit;
    @Nullable
    protected Long offset;
    protected List<ExpansionPath<T>> expansionPaths = emptyList();
    protected List<HttpQueryParameter> additionalQueryParameters = emptyList();
    protected final String endpoint;
    protected final Function<HttpResponse, PagedSearchResult<T>> resultMapper;
    protected final S searchModel;
    protected final E expansionModel;
    protected final Function<MetaModelSearchDslBuilder<T, C, S, E>, C> searchDslBuilderFunction;

    public MetaModelSearchDslBuilder(final String endpoint, final Function<HttpResponse, PagedSearchResult<T>> resultMapper,
                                     final S searchModel, final E expansionModel, final Function<MetaModelSearchDslBuilder<T, C, S, E>, C> searchDslBuilderFunction) {
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
        this.searchModel = searchModel;
        this.expansionModel = expansionModel;
        this.searchDslBuilderFunction = searchDslBuilderFunction;
    }

    public MetaModelSearchDslBuilder(final MetaModelSearchDslImpl<T, C, S, E> template) {
        this(template.endpoint(), r -> template.deserialize(r), template.getSearchModel(), template.getExpansionModel(), template.searchDslBuilderFunction);
        text = template.text();
        facets = template.facets();
        resultFilters = template.resultFilters();
        queryFilters = template.queryFilters();
        facetFilters = template.facetFilters();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
        expansionPaths = template.expansionPaths();
        additionalQueryParameters = template.additionalQueryParameters();
    }

    public MetaModelSearchDslBuilder<T, C, S, E> text(@Nullable final LocalizedStringEntry text) {
        this.text = text;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, E> facets(final List<FacetExpression<T>> facets) {
        this.facets = facets;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, E> resultFilters(final List<FilterExpression<T>> resultFilters) {
        this.resultFilters = resultFilters;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, E> queryFilters(final List<FilterExpression<T>> queryFilters) {
        this.queryFilters = queryFilters;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, E> facetFilters(final List<FilterExpression<T>> facetFilters) {
        this.facetFilters = facetFilters;
        return this;
    }
    
    public MetaModelSearchDslBuilder<T, C, S, E> sort(final List<SortExpression<T>> sort) {
        this.sort = sort;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, E> limit(@Nullable final Long limit) {
        this.limit = limit;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, E> offset(@Nullable final Long offset) {
        this.offset = offset;
        return this;
    }


    public MetaModelSearchDslBuilder<T, C, S, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public MetaModelSearchDslBuilder<T, C, S, E> additionalQueryParameters(final List<HttpQueryParameter> additionalQueryParameters) {
        this.additionalQueryParameters = additionalQueryParameters;
        return this;
    }

    @Override
    public C build() {
        return searchDslBuilderFunction.apply(this);
    }
}
