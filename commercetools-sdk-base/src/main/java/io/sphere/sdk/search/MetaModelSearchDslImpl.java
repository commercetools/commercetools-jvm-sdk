package io.sphere.sdk.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestUtils;
import io.sphere.sdk.expansion.ExpansionDslUtils;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.http.*;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStringEntry;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.search.SearchParameterKeys.*;
import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T> type of the search result
 * @param <C> type of the class implementing this class
 * @param <S> type of the sort model
 * @param <F> type of the facet model
 * @param <E> type of the expansion model
 */
public abstract class MetaModelSearchDslImpl<T, C extends MetaModelSearchDsl<T, C, S, L, F, E>, S, L, F, E> extends Base implements MetaModelSearchDsl<T, C, S, L, F, E> {

    @Nullable
    final LocalizedStringEntry text;
    @Nullable
    final Boolean fuzzy;
    @Nullable
    final Integer fuzzyLevel;
    final List<FacetExpression<T>> facets;
    final List<FilterExpression<T>> resultFilters;
    final List<FilterExpression<T>> queryFilters;
    final List<FilterExpression<T>> facetFilters;
    final List<FacetedSearchExpression<T>> facetedSearch;
    final List<SortExpression<T>> sort;
    @Nullable
    final Long limit;
    @Nullable
    final Long offset;
    final List<ExpansionPath<T>> expansionPaths;
    final List<NameValuePair> additionalQueryParameters;
    final String endpoint;
    final S sortModel;
    final L filterModel;
    final F facetModel;
    final E expansionModel;
    final Function<HttpResponse, PagedSearchResult<T>> resultMapper;
    final Function<MetaModelSearchDslBuilder<T, C, S, L, F, E>, C> searchDslBuilderFunction;

    public MetaModelSearchDslImpl(@Nullable final LocalizedStringEntry text, @Nullable final Boolean fuzzy, @Nullable final Integer fuzzyLevel,
                                  final List<FacetExpression<T>> facets, final List<FilterExpression<T>> resultFilters,
                                  final List<FilterExpression<T>> queryFilters, final List<FilterExpression<T>> facetFilters,
                                  final List<FacetedSearchExpression<T>> facetedSearch,
                                  final List<SortExpression<T>> sort, @Nullable final Long limit, @Nullable final Long offset,
                                  final String endpoint, final Function<HttpResponse, PagedSearchResult<T>> resultMapper,
                                  final List<ExpansionPath<T>> expansionPaths, final List<NameValuePair> additionalQueryParameters,
                                  final S sortModel, final L filterModel, final F facetModel, final E expansionModel, final Function<MetaModelSearchDslBuilder<T, C, S, L, F, E>, C> searchDslBuilderFunction) {
        Optional.ofNullable(offset).ifPresent(presentOffset -> {
            if (presentOffset < MIN_OFFSET || presentOffset > MAX_OFFSET) {
                throw new IllegalArgumentException(format("The offset parameter must be in the range of [%d..%d], but was %d.", MIN_OFFSET, MAX_OFFSET, presentOffset));
            }
        });
        this.searchDslBuilderFunction = requireNonNull(searchDslBuilderFunction);
        this.text = text;
        this.fuzzy = fuzzy;
        this.fuzzyLevel = fuzzyLevel;
        this.facets = requireNonNull(facets);
        this.resultFilters = requireNonNull(resultFilters);
        this.queryFilters = requireNonNull(queryFilters);
        this.facetFilters = requireNonNull(facetFilters);
        this.facetedSearch = requireNonNull(facetedSearch);
        this.sort = requireNonNull(sort);
        this.limit = limit;
        this.offset = offset;
        this.endpoint = requireNonNull(endpoint);
        this.resultMapper = requireNonNull(resultMapper);
        this.expansionPaths = requireNonNull(expansionPaths);
        this.additionalQueryParameters = requireNonNull(additionalQueryParameters);
        this.filterModel = requireNonNull(filterModel);
        this.facetModel = requireNonNull(facetModel);
        this.expansionModel = requireNonNull(expansionModel);
        this.sortModel = requireNonNull(sortModel);
    }

    public MetaModelSearchDslImpl(final String endpoint, final TypeReference<PagedSearchResult<T>> pagedSearchResultTypeReference,
                                  final S sortModel, final L filterModel, final F facetModel, final E expansionModel, final Function<MetaModelSearchDslBuilder<T, C, S, L, F, E>, C> searchDslBuilderFunction,
                                  final List<NameValuePair> additionalQueryParameters) {
        this(null, null, null, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), null, null, endpoint, httpResponse -> SphereRequestUtils.deserialize(httpResponse, pagedSearchResultTypeReference),
                emptyList(), additionalQueryParameters, sortModel, filterModel, facetModel, expansionModel, searchDslBuilderFunction);
    }

    public MetaModelSearchDslImpl(final String endpoint, final TypeReference<PagedSearchResult<T>> pagedSearchResultTypeReference,
                                  final S sortModel, final L filterModel, final F facetModel, final E expansionModel, final Function<MetaModelSearchDslBuilder<T, C, S, L, F, E>, C> searchDslBuilderFunction) {
        this(endpoint, pagedSearchResultTypeReference, sortModel, filterModel, facetModel, expansionModel, searchDslBuilderFunction, emptyList());
    }

    public MetaModelSearchDslImpl(final MetaModelSearchDslBuilder<T, C, S, L, F, E> builder) {
        this(builder.text, builder.fuzzy, builder.fuzzyLevel, builder.facets, builder.resultFilters, builder.queryFilters, builder.facetFilters, builder.facetedSearch, builder.sort,
                builder.limit, builder.offset, builder.endpoint, builder.resultMapper, builder.expansionPaths, builder.additionalQueryParameters,
                builder.sortModel, builder.filterModel, builder.facetModel, builder.expansionModel, builder.searchDslBuilderFunction);
    }

    @Override
    public C withText(@Nullable final LocalizedStringEntry text) {
        return copyBuilder().text(text).build();
    }

    @Override
    public C withText(final Locale locale, final String text) {
        final LocalizedStringEntry locStringEntry = LocalizedStringEntry.of(requireNonNull(locale), requireNonNull(text));
        return withText(locStringEntry);
    }

    @Override
    public C withFuzzy(final Boolean fuzzy) {
        return copyBuilder().fuzzy(fuzzy).build();
    }

    @Override
    public C withFuzzyLevel(final Integer fuzzyLevel) {
        return copyBuilder().fuzzyLevel(fuzzyLevel).build();
    }

    @Override
    public C withFacets(final List<FacetExpression<T>> facets) {
        return copyBuilder().facets(facets).build();
    }

    @Override
    public C withFacets(final FacetExpression<T> facetExpression) {
        return withFacets(singletonList(requireNonNull(facetExpression)));
    }

    @Override
    public C withFacets(final Function<F, FacetExpression<T>> m) {
        return withFacets(m.apply(facetModel));
    }

    @Override
    public C plusFacets(final List<FacetExpression<T>> facetExpressions) {
        return withFacets(listOf(facets(), facetExpressions));
    }

    @Override
    public C plusFacets(final FacetExpression<T> facetExpression) {
        return plusFacets(singletonList(requireNonNull(facetExpression)));
    }

    @Override
    public C plusFacets(final Function<F, FacetExpression<T>> m) {
        return plusFacets(m.apply(facetModel));
    }

    @Override
    public C withResultFilters(final List<FilterExpression<T>> filterExpressions) {
        return copyBuilder().resultFilters(filterExpressions).build();
    }

    @Override
    public C withResultFilters(final Function<L, List<FilterExpression<T>>> m) {
        return withResultFilters(m.apply(filterModel));
    }

    @Override
    public C plusResultFilters(final List<FilterExpression<T>> filterExpressions) {
        return withResultFilters(listOf(resultFilters(), filterExpressions));
    }

    @Override
    public C plusResultFilters(final Function<L, List<FilterExpression<T>>> m) {
        return plusResultFilters(m.apply(filterModel));
    }

    @Override
    public C withQueryFilters(final List<FilterExpression<T>> filterExpressions) {
        return copyBuilder().queryFilters(filterExpressions).build();
    }

    @Override
    public C withQueryFilters(final Function<L, List<FilterExpression<T>>> m) {
        return withQueryFilters(m.apply(filterModel));
    }

    @Override
    public C plusQueryFilters(final List<FilterExpression<T>> filterExpressions) {
        return withQueryFilters(listOf(queryFilters(), filterExpressions));
    }

    @Override
    public C plusQueryFilters(final Function<L, List<FilterExpression<T>>> m) {
        return plusQueryFilters(m.apply(filterModel));
    }

    @Override
    public C withFacetFilters(final List<FilterExpression<T>> filterExpressions) {
        return copyBuilder().facetFilters(filterExpressions).build();
    }

    @Override
    public C withFacetFilters(final Function<L, List<FilterExpression<T>>> m) {
        return withFacetFilters(m.apply(filterModel));
    }

    @Override
    public C plusFacetFilters(final List<FilterExpression<T>> filterExpressions) {
        return withFacetFilters(listOf(facetFilters(), filterExpressions));
    }

    @Override
    public C plusFacetFilters(final Function<L, List<FilterExpression<T>>> m) {
        return plusFacetFilters(m.apply(filterModel));
    }

    @Override
    public C withFacetedSearch(final List<FacetedSearchExpression<T>> facetedSearchExpressions) {
        return copyBuilder().facetedSearch(facetedSearchExpressions).build();
    }

    @Override
    public C withFacetedSearch(final FacetedSearchExpression<T> facetedSearchExpression) {
        return withFacetedSearch(singletonList(requireNonNull(facetedSearchExpression)));
    }

    @Override
    public C plusFacetedSearch(final List<FacetedSearchExpression<T>> facetedSearchExpressions) {
        return withFacetedSearch(listOf(facetedSearch(), facetedSearchExpressions));
    }

    @Override
    public C plusFacetedSearch(final FacetedSearchExpression<T> facetedSearchExpression) {
        return plusFacetedSearch(singletonList(requireNonNull(facetedSearchExpression)));
    }

    @Override
    public C withSort(final List<SortExpression<T>> sortExpressions) {
        return copyBuilder().sort(sortExpressions).build();
    }

    @Override
    public C withSort(final SortExpression<T> sortExpression) {
        return withSort(singletonList(sortExpression));
    }

    @Override
    public C withSort(final Function<S, SortExpression<T>> m) {
        return withSort(m.apply(sortModel));
    }

    @Override
    public C plusSort(final List<SortExpression<T>> sortExpressions) {
        return withSort(listOf(sort(), sortExpressions));
    }

    @Override
    public C plusSort(final SortExpression<T> sortExpression) {
        return plusSort(singletonList(requireNonNull(sortExpression)));
    }

    @Override
    public C plusSort(final Function<S, SortExpression<T>> m) {
        return plusSort(m.apply(sortModel));
    }

    @Override
    public C withLimit(final Long limit) {
        return copyBuilder().limit(limit).build();
    }

    @Override
    public C withOffset(final Long offset) {
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
    public C withExpansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return withExpansionPaths(m.apply(expansionModel).expansionPaths());
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
    public C plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return plusExpansionPaths(m.apply(expansionModel).expansionPaths());
    }

    @Override
    public C withExpansionPaths(final String expansionPath) {
        return withExpansionPaths(ExpansionPath.of(expansionPath));
    }

    @Override
    public C plusExpansionPaths(final String expansionPath) {
        return plusExpansionPaths(ExpansionPath.of(expansionPath));
    }

    @Override
    public C withAdditionalQueryParameter(final NameValuePair pair) {
        final List<NameValuePair> params = additionalQueryParameters();
        final List<NameValuePair> resultingParameters = new LinkedList<>(params);
        resultingParameters.add(pair);
        return withAdditionalQueryParameters(resultingParameters);
    }

    protected C withAdditionalQueryParameters(final List<NameValuePair> pairs) {
        return copyBuilder().additionalQueryParameters(pairs).build();
    }

    @Override
    @Nullable
    public LocalizedStringEntry text() {
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
    public List<FacetedSearchExpression<T>> facetedSearch() {
        return facetedSearch;
    }

    @Override
    public List<SortExpression<T>> sort() {
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

    @Nullable
    @Override
    public Boolean isFuzzy() {
        return fuzzy;
    }

    @Nullable
    @Override
    public Integer fuzzyLevel() {
        return fuzzyLevel;
    }

    protected List<NameValuePair> additionalQueryParameters() {
        return additionalQueryParameters;
    }

    protected MetaModelSearchDslBuilder<T, C, S, L, F, E> copyBuilder() {
        return new MetaModelSearchDslBuilder<>(this);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final String additions = queryParametersToString(true);
        return HttpRequestIntent.of(HttpMethod.POST, endpoint, HttpHeaders.of(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded"), StringHttpRequestBody.of(additions));
    }

    @Override
    public PagedSearchResult<T> deserialize(final HttpResponse httpResponse) {
        return resultMapper.apply(httpResponse);
    }

    String queryParametersToString(final boolean urlEncoded) {
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        Optional.ofNullable(text()).ifPresent(t -> builder.add(TEXT + "." + t.getLocale().toLanguageTag(), t.getValue(), urlEncoded));
        facets().forEach(f -> builder.add(FACET, f.expression(), urlEncoded));
        Optional.ofNullable(isFuzzy()).ifPresent(b -> builder.add(FUZZY, b.toString(), urlEncoded));
        Optional.ofNullable(fuzzyLevel()).ifPresent(b -> builder.add(FUZZY_LEVEL, b.toString(), urlEncoded));
        resultFilters().forEach(f -> builder.add(FILTER_RESULTS, f.expression(), urlEncoded));
        queryFilters().forEach(f -> builder.add(FILTER_QUERY, f.expression(), urlEncoded));
        facetFilters().forEach(f -> builder.add(FILTER_FACETS, f.expression(), urlEncoded));
        facetedSearch().forEach(f -> {
            builder.add(FACET, f.facetExpression().expression(), urlEncoded);
            f.filterExpressions().forEach(filter -> {
                builder.add(FILTER_RESULTS, filter.expression(), urlEncoded);
                builder.add(FILTER_FACETS, filter.expression(), urlEncoded);
            });
        });
        sort().forEach(s -> builder.add(SORT, s.expression(), urlEncoded));
        if (!facets().isEmpty() || !facetedSearch().isEmpty()) {
            builder.add("formatBooleanFacet", Boolean.TRUE.toString(), urlEncoded);
        }
        Optional.ofNullable(limit()).ifPresent(l -> builder.add(LIMIT, l.toString(), urlEncoded));
        Optional.ofNullable(offset()).ifPresent(o -> builder.add(OFFSET, o.toString(), urlEncoded));
        expansionPaths().forEach(path -> builder.add(EXPAND, path.toSphereExpand(), urlEncoded));
        additionalQueryParameters().forEach(p -> builder.add(p.getName(), p.getValue(), urlEncoded));
        return builder.build();
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
        final String readablePath = queryParametersToString(false);

        return getClass().getSimpleName() + "{" +
                "body=" + readablePath +
                ", text=" + text +
                ", fuzzy=" + fuzzy +
                ", fuzzyLevel=" + fuzzyLevel +
                ", facets=" + facets +
                ", resultFilters=" + resultFilters +
                ", queryFilters=" + queryFilters +
                ", facetFilters=" + facetFilters +
                ", facetedSearch=" + facetedSearch +
                ", sort=" + sort +
                ", additionalQueryParameters=" + additionalQueryParameters +
                ", limit=" + limit +
                ", offset=" + offset +
                ", endpoint='" + endpoint + '\'' +
                ", resultMapper=" + resultMapper +
                ", request=" + httpRequestIntent() +
                '}';
    }

    S getSortModel() {
        return sortModel;
    }

    L getFilterModel() {
        return filterModel;
    }

    F getFacetModel() {
        return facetModel;
    }

    E getExpansionModel() {
        return expansionModel;
    }

    Function<HttpResponse, PagedSearchResult<T>> getResultMapper() {
        return resultMapper;
    }
}
