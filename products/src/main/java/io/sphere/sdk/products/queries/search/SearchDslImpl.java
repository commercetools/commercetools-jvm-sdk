package io.sphere.sdk.products.queries.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.QueryParameter;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.UrlQueryBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.products.queries.search.SearchParameterKeys.*;
import static java.util.Collections.emptyList;

public class SearchDslImpl<T> extends Base implements SearchDsl<T> {

    private final Locale lang;
    private final Optional<String> text;
    private final List<Facet<T>> facets;
    private final List<Filter<T>> filters;
    private final List<Filter<T>> filterQueries;
    private final List<Filter<T>> filterFacets;
    private final List<SearchSort<T>> sort;
    private final Optional<Long> limit;
    private final Optional<Long> offset;
    private final List<QueryParameter> additionalQueryParameters;
    private final Function<HttpResponse, PagedSearchResult<T>> resultMapper;
    private final String endpoint;

    public SearchDslImpl(final String endpoint, final Locale lang, final Optional<String> text, final List<Facet<T>> facets,
                         final List<Filter<T>> filters, final List<Filter<T>> filterQueries, final List<Filter<T>> filterFacets,
                         final List<SearchSort<T>> sort, final Optional<Long> limit, final Optional<Long> offset,
                         final List<QueryParameter> additionalQueryParameters, Function<HttpResponse, PagedSearchResult<T>> resultMapper) {
        this.lang = lang;
        this.text = text;
        this.facets = facets;
        this.filters = filters;
        this.filterQueries = filterQueries;
        this.filterFacets = filterFacets;
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
        this.additionalQueryParameters = additionalQueryParameters;
        this.resultMapper = resultMapper;
        this.endpoint = endpoint;
    }

    public SearchDslImpl(final String endpoint, final Function<HttpResponse, PagedSearchResult<T>> resultMapper,
                         final List<QueryParameter> additionalQueryParameters, final Locale lang) {
        this(endpoint, lang, Optional.empty(), emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), Optional.empty(), Optional.empty(), additionalQueryParameters, resultMapper);
    }

    public SearchDslImpl(final String endpoint, final TypeReference<PagedSearchResult<T>> typeReference,
                         final List<QueryParameter> additionalQueryParameters, final Locale lang) {
        this(endpoint, resultMapperOf(typeReference), additionalQueryParameters, lang);
    }

    private static <T> Function<HttpResponse, PagedSearchResult<T>> resultMapperOf(TypeReference<PagedSearchResult<T>> typeReference) {
        return httpResponse -> JsonUtils.readObjectFromJsonString(typeReference, httpResponse.getResponseBody());
    }

    @Override
    public SearchDsl<T> withText(Optional<String> text) {
        return copyBuilder().text(text).build();
    }

    @Override
    public SearchDsl<T> withFacets(List<Facet<T>> facets) {
        return copyBuilder().facets(facets).build();
    }

    @Override
    public SearchDsl<T> withFilters(List<Filter<T>> filters) {
        return copyBuilder().filters(filters).build();
    }

    @Override
    public SearchDsl<T> withFilterQueries(List<Filter<T>> filterQueries) {
        return copyBuilder().filterQueries(filterQueries).build();
    }

    @Override
    public SearchDsl<T> withFilterFacets(List<Filter<T>> filterFacets) {
        return copyBuilder().filterFacets(filterFacets).build();
    }

    @Override
    public SearchDsl<T> withSort(List<SearchSort<T>> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public SearchDsl<T> withLimit(final long limit) {
        return copyBuilder().limit(Optional.of(limit)).build();
    }

    @Override
    public SearchDsl<T> withOffset(long offset) {
        return copyBuilder().offset(Optional.of(offset)).build();
    }

    @Override
    public SearchDsl<T> withAdditionalQueryParameters(List<QueryParameter> additionalQueryParameters) {
        return copyBuilder().additionalQueryParameters(additionalQueryParameters).build();
    }

    @Override
    public Locale lang() {
        return lang;
    }

    @Override
    public Optional<String> text() {
        return text;
    }

    @Override
    public List<Facet<T>> facets() {
        return facets;
    }

    @Override
    public List<Filter<T>> filters() {
        return filters;
    }

    @Override
    public List<Filter<T>> filterQueries() {
        return filterQueries;
    }

    @Override
    public List<Filter<T>> filterFacets() {
        return filterFacets;
    }

    @Override
    public List<SearchSort<T>> sort() {
        return sort;
    }

    @Override
    public Optional<Long> limit() {
        return limit;
    }

    @Override
    public Optional<Long> offset() {
        return offset;
    }

    @Override
    public List<QueryParameter> additionalQueryParameters() {
        return additionalQueryParameters;
    }

    @Override
    public Function<HttpResponse, PagedSearchResult<T>> resultMapper() {
        return resultMapper;
    }

    @Override
    public HttpRequest httpRequest() {
        final String additions = queryParametersToString(true);
        return HttpRequest.of(HttpMethod.GET, endpoint + (additions.length() > 1 ? additions : ""));
    }

    private String queryParametersToString(final boolean urlEncoded) {
        final UrlQueryBuilder builder = new UrlQueryBuilder();
        builder.add(LANG, lang().getLanguage(), urlEncoded);
        text().ifPresent(t -> builder.add(TEXT, t, urlEncoded));
        facets().forEach(f -> builder.add(FACET, f.toSphereFacet(), urlEncoded));
        filters().forEach(f -> builder.add(FILTER, f.toSphereFilter(), urlEncoded));
        filterQueries().forEach(f -> builder.add(FILTER_QUERY, f.toSphereFilter(), urlEncoded));
        filterFacets().forEach(f -> builder.add(FILTER_FACETS, f.toSphereFilter(), urlEncoded));
        sort().forEach(s -> builder.add(SORT, s.toSphereSort(), urlEncoded));
        limit().ifPresent(l -> builder.add(LIMIT, l.toString(), urlEncoded));
        offset().ifPresent(o -> builder.add(OFFSET, o.toString(), urlEncoded));
        additionalQueryParameters().forEach(p -> builder.add(p.getKey(), p.getValue(), urlEncoded));
        return "?" + builder.toString();
    }

    @Override
    public String endpoint() {
        return endpoint;
    }

    private SearchDslBuilder<T> copyBuilder() {
        return new SearchDslBuilder<>(this);
    }

    @Override
    public String toString() {
        final String readablePath = endpoint + queryParametersToString(false);

        return "SearchDslImpl{" +
                "lang=" + lang +
                ", text=" + text +
                ", facets=" + facets +
                ", filters=" + filters +
                ", filterQueries=" + filterQueries +
                ", filterFacets=" + filterFacets +
                ", sort=" + sort +
                ", limit=" + limit +
                ", offset=" + offset +
                ", additionalQueryParameters=" + additionalQueryParameters +
                ", resultMapper=" + resultMapper +
                ", readablePath=" + readablePath +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}
