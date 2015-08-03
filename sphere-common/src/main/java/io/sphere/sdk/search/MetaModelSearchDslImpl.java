package io.sphere.sdk.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.models.LocalizedStringsEntry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.search.SearchParameterKeys.*;
import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T> type of the search result
 * @param <C> type of the class implementing this class
 * @param <S> type of the search model
 * @param <E> type of the expansion model
 */
public abstract class MetaModelSearchDslImpl<T, C extends MetaModelSearchDsl<T, C, S, E>, S, E> extends SphereRequestBase implements MetaModelSearchDsl<T, C, S, E> {

    @Nullable
    final LocalizedStringsEntry text;
    final List<FacetExpression<T>> facets;
    final List<FilterExpression<T>> resultFilters;
    final List<FilterExpression<T>> queryFilters;
    final List<FilterExpression<T>> facetFilters;
    final List<SearchSort<T>> sort;
    @Nullable
    final Long limit;
    @Nullable
    final Long offset;
    final List<ExpansionPath<T>> expansionPaths;
    final List<HttpQueryParameter> additionalQueryParameters;
    final String endpoint;
    final S searchModel;
    final E expansionModel;
    final Function<HttpResponse, PagedSearchResult<T>> resultMapper;
    final Function<MetaModelSearchDslBuilder<T, C, S, E>, C> searchDslBuilderFunction;

    public MetaModelSearchDslImpl(@Nullable final LocalizedStringsEntry text, final List<FacetExpression<T>> facets, final List<FilterExpression<T>> resultFilters,
                                  final List<FilterExpression<T>> queryFilters, final List<FilterExpression<T>> facetFilters,
                                  final List<SearchSort<T>> sort, @Nullable final Long limit, @Nullable final Long offset,
                                  final String endpoint, final Function<HttpResponse, PagedSearchResult<T>> resultMapper,
                                  final List<ExpansionPath<T>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters,
                                  final S searchModel, final E expansionModel, final Function<MetaModelSearchDslBuilder<T, C, S, E>, C> searchDslBuilderFunction) {
        Optional.ofNullable(offset).ifPresent(presentOffset -> {
            if (presentOffset < MIN_OFFSET || presentOffset > MAX_OFFSET) {
                throw new IllegalArgumentException(format("The offset parameter must be in the range of [%d..%d], but was %d.", MIN_OFFSET, MAX_OFFSET, presentOffset));
            }
        });
        this.searchDslBuilderFunction = requireNonNull(searchDslBuilderFunction);
        this.text = text;
        this.facets = requireNonNull(facets);
        this.resultFilters = requireNonNull(resultFilters);
        this.queryFilters = requireNonNull(queryFilters);
        this.facetFilters = requireNonNull(facetFilters);
        this.sort = requireNonNull(sort);
        this.limit = limit;
        this.offset = offset;
        this.endpoint = requireNonNull(endpoint);
        this.resultMapper = requireNonNull(resultMapper);
        this.expansionPaths = requireNonNull(expansionPaths);
        this.additionalQueryParameters = requireNonNull(additionalQueryParameters);
        this.expansionModel = requireNonNull(expansionModel);
        this.searchModel = requireNonNull(searchModel);
    }

    public MetaModelSearchDslImpl(final String endpoint, final TypeReference<PagedSearchResult<T>> pagedSearchResultTypeReference,
                                  final S searchModel, final E expansionModel, final Function<MetaModelSearchDslBuilder<T, C, S, E>, C> searchDslBuilderFunction,
                                  final List<HttpQueryParameter> additionalQueryParameters) {
        this(null, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), null, null, endpoint, httpResponse -> deserialize(httpResponse, pagedSearchResultTypeReference),
                emptyList(), additionalQueryParameters, searchModel, expansionModel, searchDslBuilderFunction);
    }

    public MetaModelSearchDslImpl(final String endpoint, final TypeReference<PagedSearchResult<T>> pagedSearchResultTypeReference,
                                  final S searchModel, final E expansionModel, final Function<MetaModelSearchDslBuilder<T, C, S, E>, C> searchDslBuilderFunction) {
        this(endpoint, pagedSearchResultTypeReference, searchModel, expansionModel, searchDslBuilderFunction, emptyList());
    }

    public MetaModelSearchDslImpl(final MetaModelSearchDslBuilder<T, C, S, E> builder) {
        this(builder.text, builder.facets, builder.resultFilters, builder.queryFilters, builder.facetFilters, builder.sort,
                builder.limit, builder.offset, builder.endpoint, builder.resultMapper, builder.expansionPaths, builder.additionalQueryParameters,
                builder.searchModel, builder.expansionModel, builder.searchDslBuilderFunction);
    }

    @Override
    public C withText(@Nullable final LocalizedStringsEntry text) {
        return copyBuilder().text(text).build();
    }

    @Override
    public C withText(final Locale locale, final String text) {
        final LocalizedStringsEntry locStringEntry = LocalizedStringsEntry.of(requireNonNull(locale), requireNonNull(text));
        return withText(locStringEntry);
    }

    @Override
    public C withFacets(final List<FacetExpression<T>> facets) {
        return copyBuilder().facets(facets).build();
    }

    @Override
    public C withFacets(final FacetExpression<T> facet) {
        return withFacets(singletonList(requireNonNull(facet)));
    }

    @Override
    public C withFacets(final Function<S, FacetExpression<T>> m) {
        return withFacets(m.apply(searchModel));
    }

    @Override
    public C plusFacets(final List<FacetExpression<T>> facets) {
        return withFacets(listOf(facets(), facets));
    }

    @Override
    public C plusFacets(final FacetExpression<T> facet) {
        return plusFacets(singletonList(requireNonNull(facet)));
    }

    @Override
    public C plusFacets(final Function<S, FacetExpression<T>> m) {
        return plusFacets(m.apply(searchModel));
    }

    @Override
    public C withResultFilters(final List<FilterExpression<T>> resultFilters) {
        return copyBuilder().resultFilters(resultFilters).build();
    }

    @Override
    public C withResultFilters(final FilterExpression<T> resultFilter) {
        return withResultFilters(singletonList(requireNonNull(resultFilter)));
    }

    @Override
    public C withResultFilters(final Function<S, FilterExpression<T>> m) {
        return withResultFilters(m.apply(searchModel));
    }

    @Override
    public C plusResultFilters(final List<FilterExpression<T>> resultFilters) {
        return withResultFilters(listOf(resultFilters(), resultFilters));
    }

    @Override
    public C plusResultFilters(final FilterExpression<T> resultFilter) {
        return plusResultFilters(singletonList(requireNonNull(resultFilter)));
    }

    @Override
    public C plusResultFilters(final Function<S, FilterExpression<T>> m) {
        return plusResultFilters(m.apply(searchModel));
    }

    @Override
    public C withQueryFilters(final List<FilterExpression<T>> queryFilters) {
        return copyBuilder().queryFilters(queryFilters).build();
    }

    @Override
    public C withQueryFilters(final FilterExpression<T> queryFilter) {
        return withQueryFilters(singletonList(requireNonNull(queryFilter)));
    }

    @Override
    public C withQueryFilters(final Function<S, FilterExpression<T>> m) {
        return withQueryFilters(m.apply(searchModel));
    }

    @Override
    public C plusQueryFilters(final List<FilterExpression<T>> queryFilters) {
        return withQueryFilters(listOf(queryFilters(), queryFilters));
    }

    @Override
    public C plusQueryFilters(final FilterExpression<T> queryFilter) {
        return plusQueryFilters(singletonList(requireNonNull(queryFilter)));
    }

    @Override
    public C plusQueryFilters(final Function<S, FilterExpression<T>> m) {
        return plusQueryFilters(m.apply(searchModel));
    }

    @Override
    public C withFacetFilters(final List<FilterExpression<T>> facetFilters) {
        return copyBuilder().facetFilters(facetFilters).build();
    }

    @Override
    public C withFacetFilters(final FilterExpression<T> facetFilter) {
        return withFacetFilters(singletonList(requireNonNull(facetFilter)));
    }

    @Override
    public C withFacetFilters(final Function<S, FilterExpression<T>> m) {
        return withFacetFilters(m.apply(searchModel));
    }

    @Override
    public C plusFacetFilters(final List<FilterExpression<T>> facetFilters) {
        return withFacetFilters(listOf(facetFilters(), facetFilters));
    }

    @Override
    public C plusFacetFilters(final FilterExpression<T> facetFilter) {
        return plusFacetFilters(singletonList(requireNonNull(facetFilter)));
    }

    @Override
    public C plusFacetFilters(final Function<S, FilterExpression<T>> m) {
        return plusFacetFilters(m.apply(searchModel));
    }

    private C withSort(final List<SearchSort<T>> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public C withSort(final Function<S, SearchSort<T>> m) {
        return withSort(m.apply(searchModel));
    }

    @Override
    public C withSort(final SearchSort<T> sort) {
        return withSort(singletonList(sort));
    }

    @Override
    public C withLimit(final long limit) {
        return copyBuilder().limit(limit).build();
    }

    @Override
    public C withOffset(final long offset) {
        return copyBuilder().offset(offset).build();
    }

    @Override
    public C withExpansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return copyBuilder().expansionPaths(expansionPaths).build();
    }

    @Override
    public C withExpansionPaths(final ExpansionPath<T> expansionPath) {
        return withExpansionPaths(singletonList(requireNonNull(expansionPath)));
    }

    @Override
    public C withExpansionPaths(final Function<E, ExpansionPath<T>> m) {
        return withExpansionPaths(singletonList(m.apply(expansionModel)));
    }

    @Override
    public C plusExpansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return withExpansionPaths(listOf(expansionPaths(), expansionPaths));
    }

    @Override
    public C plusExpansionPaths(final ExpansionPath<T> expansionPath) {
        return plusExpansionPaths(singletonList(requireNonNull(expansionPath)));
    }

    @Override
    public C plusExpansionPaths(final Function<E, ExpansionPath<T>> m) {
        return plusExpansionPaths(m.apply(expansionModel));
    }

    @Override
    @Nullable
    public LocalizedStringsEntry text() {
        return text;
    }

    @Override
    public List<FacetExpression<T>> facets() {
        return facets;
    }

    @Override
    public List<FilterExpression<T>> resultFilters() {
        return resultFilters;
    }

    @Override
    public List<FilterExpression<T>> queryFilters() {
        return queryFilters;
    }

    @Override
    public List<FilterExpression<T>> facetFilters() {
        return facetFilters;
    }

    @Override
    public List<SearchSort<T>> sort() {
        return sort;
    }

    @Override
    @Nullable
    public Long limit() {
        return limit;
    }

    @Override
    @Nullable
    public Long offset() {
        return offset;
    }

    @Override
    public String endpoint() {
        return endpoint;
    }

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return expansionPaths;
    }

    protected List<HttpQueryParameter> additionalQueryParameters() {
        return additionalQueryParameters;
    }

    protected MetaModelSearchDslBuilder<T, C, S, E> copyBuilder() {
        return new MetaModelSearchDslBuilder<>(this);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final String additions = queryParametersToString(true);
        return HttpRequestIntent.of(HttpMethod.GET, endpoint + (additions.length() > 1 ? additions : ""));
    }

    @Override
    public PagedSearchResult<T> deserialize(final HttpResponse httpResponse) {
        return resultMapper.apply(httpResponse);
    }

    String queryParametersToString(final boolean urlEncoded) {
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        Optional.ofNullable(text()).ifPresent(t -> builder.add(TEXT + "." + t.getLocale().getLanguage(), t.getValue(), urlEncoded));
        facets().forEach(f -> builder.add(FACET, f.toSphereFacet(), urlEncoded));
        resultFilters().forEach(f -> builder.add(FILTER, f.toSphereFilter(), urlEncoded));
        queryFilters().forEach(f -> builder.add(FILTER_QUERY, f.toSphereFilter(), urlEncoded));
        facetFilters().forEach(f -> builder.add(FILTER_FACETS, f.toSphereFilter(), urlEncoded));
        sort().forEach(s -> builder.add(SORT, s.toSphereSort(), urlEncoded));
        Optional.ofNullable(limit()).ifPresent(l -> builder.add(LIMIT, l.toString(), urlEncoded));
        Optional.ofNullable(offset()).ifPresent(o -> builder.add(OFFSET, o.toString(), urlEncoded));
        expansionPaths().forEach(path -> builder.add(EXPAND, path.toSphereExpand(), urlEncoded));
        additionalQueryParameters().forEach(p -> builder.add(p.getKey(), p.getValue(), urlEncoded));
        return builder.toStringWithOptionalQuestionMark();
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && (o instanceof Search) && ((Search)o).httpRequestIntent().getPath().equals(httpRequestIntent().getPath());
    }

    @Override
    public final int hashCode() {
        return httpRequestIntent().getPath().hashCode();
    }

    @Override
    public String toString() {
        final String readablePath = endpoint + queryParametersToString(false);

        return "SearchDslImpl{" +
                ", text=" + text +
                ", facets=" + facets +
                ", resultFilters=" + resultFilters +
                ", queryFilters=" + queryFilters +
                ", facetFilters=" + facetFilters +
                ", sort=" + sort +
                ", additionalQueryParameters=" + additionalQueryParameters +
                ", limit=" + limit +
                ", offset=" + offset +
                ", endpoint='" + endpoint + '\'' +
                ", resultMapper=" + resultMapper +
                ", readablePath=" + readablePath +
                ", request=" + httpRequestIntent() +
                '}';
    }

    S getSearchModel() {
        return searchModel;
    }

    E getExpansionModel() {
        return expansionModel;
    }

    Function<HttpResponse, PagedSearchResult<T>> getResultMapper() {
        return resultMapper;
    }
}
