package io.sphere.sdk.search;

import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.http.HttpQueryParameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.asList;

final class SearchDslBuilder<T> extends Base implements Builder<SearchDsl<T>>{
    private Optional<SearchText> text;
    private List<FacetExpression<T>> facets;
    private List<FilterExpression<T>> filterResults;
    private List<FilterExpression<T>> filterQueries;
    private List<FilterExpression<T>> filterFacets;
    private List<SearchSort<T>> sort;
    private Optional<Long> limit;
    private Optional<Long> offset;
    private List<HttpQueryParameter> additionalQueryParameters;
    private final Function<HttpResponse, PagedSearchResult<T>> resultMapper;
    private final String endpoint;

    SearchDslBuilder(final String endpoint, final Function<HttpResponse, PagedSearchResult<T>> resultMapper) {
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
    }

    SearchDslBuilder(final SearchDslImpl<T> template) {
        this(template.endpoint(), r -> template.deserialize(r));
        text = template.text();
        facets = template.facets();
        filterResults = template.filterResults();
        filterQueries = template.filterQueries();
        filterFacets = template.filterFacets();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
        additionalQueryParameters = template.additionalQueryParameters();
    }

    @Override
    public SearchDsl<T> build() {
        return new SearchDslImpl<>(endpoint, text, facets, filterResults, filterQueries, filterFacets,
                sort, limit, offset, additionalQueryParameters, resultMapper);
    }

    public Builder<SearchDsl<T>> text(final Optional<SearchText> text) {
        this.text = text;
        return this;
    }

    public Builder<SearchDsl<T>> facets(final List<FacetExpression<T>> facets) {
        this.facets = facets;
        return this;
    }

    public Builder<SearchDsl<T>> filterResults(final List<FilterExpression<T>> filterResults) {
        this.filterResults = filterResults;
        return this;
    }

    public Builder<SearchDsl<T>> filterQueries(final List<FilterExpression<T>> filterQueries) {
        this.filterQueries = filterQueries;
        return this;
    }

    public Builder<SearchDsl<T>> filterFacets(final List<FilterExpression<T>> filterFacets) {
        this.filterFacets = filterFacets;
        return this;
    }

    public Builder<SearchDsl<T>> sort(final SearchSort<T> sort) {
        return sort(asList(sort));
    }

    Builder<SearchDsl<T>> sort(final List<SearchSort<T>> sort) {
        this.sort = sort;
        return this;
    }

    public Builder<SearchDsl<T>> limit(final Optional<Long> limit) {
        this.limit = limit;
        return this;
    }

    public Builder<SearchDsl<T>> offset(final Optional<Long> offset) {
             this.offset = offset;
             return this;
    }

    public Builder<SearchDsl<T>> additionalQueryParameters(final List<HttpQueryParameter> additionalQueryParameters) {
        this.additionalQueryParameters = additionalQueryParameters;
        return this;
    }
}
