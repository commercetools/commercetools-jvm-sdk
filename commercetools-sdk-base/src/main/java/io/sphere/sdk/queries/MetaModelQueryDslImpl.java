package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestUtils;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.sphere.sdk.queries.QueryParameterKeys.*;
import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 * @param <Q> type of the query model
 * @param <E> type of the expansion model
 */
public abstract class MetaModelQueryDslImpl<T, C extends MetaModelQueryDsl<T, C, Q, E>, Q, E> extends Base implements MetaModelQueryDsl<T, C, Q, E> {

    final List<QueryPredicate<T>> predicate;
    final List<QuerySort<T>> sort;
    @Nullable
    final Boolean withTotal;
    @Nullable
    final Long limit;
    @Nullable
    final Long offset;
    final List<ExpansionPath<T>> expansionPaths;
    final List<NameValuePair> additionalHttpQueryParameters;
    final String endpoint;
    final Q queryModel;
    final E expansionModel;
    final Function<HttpResponse, PagedQueryResult<T>> resultMapper;
    final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction;

    public MetaModelQueryDslImpl(final List<QueryPredicate<T>> predicate, final List<QuerySort<T>> sort, @Nullable final Boolean fetchTotal, @Nullable final Long limit,
                                 @Nullable final Long offset, final String endpoint,
                                 final Function<HttpResponse, PagedQueryResult<T>> resultMapper,
                                 final List<ExpansionPath<T>> expansionPaths, final List<NameValuePair> additionalHttpQueryParameters,
                                 final Q queryModel, final E expansionModel, final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction) {
        Optional.ofNullable(offset).ifPresent(presentOffset -> {
            if (presentOffset < MIN_OFFSET || presentOffset > MAX_OFFSET) {
                throw new IllegalArgumentException(format("The offset parameter must be in the range of [%d..%d], but was %d.", MIN_OFFSET, MAX_OFFSET, presentOffset));
            }
        });
        this.queryDslBuilderFunction = requireNonNull(queryDslBuilderFunction);
        this.predicate = requireNonNull(predicate);
        this.sort = requireNonNull(sort);
        this.withTotal = fetchTotal;
        this.limit = limit;
        this.offset = offset;
        this.endpoint = requireNonNull(endpoint);
        this.resultMapper = requireNonNull(resultMapper);
        this.expansionPaths = requireNonNull(expansionPaths);
        this.additionalHttpQueryParameters = requireNonNull(additionalHttpQueryParameters);
        this.expansionModel = requireNonNull(expansionModel);
        this.queryModel = requireNonNull(queryModel);
    }

    //uses typeReference of whole result
    public MetaModelQueryDslImpl(final String endpoint, final TypeReference<PagedQueryResult<T>> pagedQueryResultTypeReference,
                                 final Q queryModel, final E expansionModel, final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction,
                                 final List<NameValuePair> additionalHttpQueryParameters) {
        this(emptyList(), emptyList(), null, null, null, endpoint, httpResponse -> SphereRequestUtils.deserialize(httpResponse, pagedQueryResultTypeReference),
                emptyList(), additionalHttpQueryParameters, queryModel, expansionModel, queryDslBuilderFunction);
    }

    //uses typeReference of the fetched objects
    public MetaModelQueryDslImpl(final String endpoint, final JavaType singleElementJavatype,
                                 final Q queryModel, final E expansionModel, final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction,
                                 final List<NameValuePair> additionalNameValuePairs) {
        this(emptyList(), emptyList(), null, null, null, endpoint, httpResponse -> SphereRequestUtils.deserialize(httpResponse, resolveJavaType(singleElementJavatype)),
                emptyList(), additionalNameValuePairs, queryModel, expansionModel, queryDslBuilderFunction);
    }

    //uses typeReference of the fetched objects
    public MetaModelQueryDslImpl(final String endpoint, final JavaType singleElementJavatype,
                                 final Q queryModel, final E expansionModel, final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction) {
        this(endpoint, singleElementJavatype, queryModel, expansionModel, queryDslBuilderFunction, emptyList());
    }

    //uses typeReference of whole result
    public MetaModelQueryDslImpl(final String endpoint, final TypeReference<PagedQueryResult<T>> pagedQueryResultTypeReference,
                                 final Q queryModel, final E expansionModel, final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction) {
        this(endpoint, pagedQueryResultTypeReference, queryModel, expansionModel, queryDslBuilderFunction, emptyList());
    }

    public MetaModelQueryDslImpl(final MetaModelQueryDslBuilder<T, C, Q, E> builder) {
        this(builder.predicate, builder.sort, builder.withTotal, builder.limit, builder.offset, builder.endpoint,
                builder.resultMapper, builder.expansionPaths, builder.additionalHttpQueryParameters, builder.queryModel,
                builder.expansionModel, builder.queryDslBuilderFunction);
    }

    @Override
    public C withPredicates(final List<QueryPredicate<T>> queryPredicates) {
        return copyBuilder().predicates(queryPredicates).build();
    }

    @Override
    public C withPredicates(final QueryPredicate<T> queryPredicate) {
        return withPredicates(singletonList(requireNonNull(queryPredicate)));
    }

    @Override
    public C withPredicates(final Function<Q, QueryPredicate<T>> predicateFunction) {
        return withPredicates(predicateFunction.apply(queryModel));
    }

    @Override
    public C plusPredicates(final List<QueryPredicate<T>> predicates) {
        return withPredicates(listOf(predicates(), predicates));
    }

    @Override
    public C withPredicates(final String queryPredicate) {
        return withPredicates(QueryPredicate.of(queryPredicate));
    }

    @Override
    public C plusPredicates(final String queryPredicate) {
        return plusPredicates(QueryPredicate.of(queryPredicate));
    }

    @Override
    public C plusPredicates(final QueryPredicate<T> queryPredicate) {
        return plusPredicates(singletonList(requireNonNull(queryPredicate)));
    }

    @Override
    public C plusPredicates(final Function<Q, QueryPredicate<T>> m) {
        return plusPredicates(m.apply(queryModel));
    }

    @Override
    public C withSort(final List<QuerySort<T>> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public C withSort(final QuerySort<T> sort) {
        return withSort(singletonList(sort));
    }

    @Override
    public C withSort(final String sort) {
        return withSort(singletonList(QuerySort.of(sort)));
    }

    @Override
    public C withSort(final Function<Q, QuerySort<T>> m) {
        return withSort(m.apply(queryModel));
    }

    @Override
    public C withSortMulti(final Function<Q, List<QuerySort<T>>> m) {
        return withSort(m.apply(queryModel));
    }

    @Override
    public C plusSort(final Function<Q, QuerySort<T>> m) {
        final QuerySort<T> additionalSort = m.apply(queryModel);
        return plusSort(additionalSort);
    }

    @Override
    public C plusSort(final List<QuerySort<T>> sort) {
        return withSort(listOf(sort(), sort));
    }

    @Override
    public C plusSort(final QuerySort<T> sort) {
        return plusSort(singletonList(sort));
    }

    @Override
    public C plusSort(final String sort) {
        return plusSort(singletonList(QuerySort.of(sort)));
    }

    @Override
    public C withFetchTotal(final boolean fetchTotal) {
        return copyBuilder().fetchTotal(fetchTotal).build();
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
    public C withExpansionPaths(final String expansionPath) {
        return withExpansionPaths(singletonList(requireNonNull(ExpansionPath.of(expansionPath))));
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
    public C plusExpansionPaths(final String expansionPath) {
        return plusExpansionPaths(singletonList(requireNonNull(ExpansionPath.of(expansionPath))));
    }

    @Override
    public C plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return plusExpansionPaths(m.apply(expansionModel).expansionPaths());
    }

    @Override
    public C withQueryParam(NameValuePair param) {
        final List<NameValuePair> params = additionalHttpQueryParameters();
        final List<NameValuePair> resultingParameters = new LinkedList<>(params);
        resultingParameters.add(param);
        return withAdditionalHttpQueryParameters(resultingParameters);
    }

    @Override
    public List<QueryPredicate<T>> predicates() {
        return predicate;
    }

    @Override
    public List<QuerySort<T>> sort() {
        return sort;
    }

    @Override
    @Nullable
    public Boolean fetchTotal() {
        return withTotal;
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

    protected List<NameValuePair> additionalHttpQueryParameters() {
        return additionalHttpQueryParameters;
    }

    protected MetaModelQueryDslBuilder<T, C, Q, E> copyBuilder() {
        return new MetaModelQueryDslBuilder<>(this);
    }

    @Override
    public final HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, completePath(endpoint, true));
    }

    @Override
    public PagedQueryResult<T> deserialize(final HttpResponse httpResponse) {
        return resultMapper.apply(httpResponse);
    }

    private String completePath(final String endpoint, final boolean urlEncoded) {
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        predicates().forEach(predicate -> builder.add(WHERE, predicate.toSphereQuery(), urlEncoded));
        sort().forEach(sort -> builder.add(SORT, sort.toSphereSort(), urlEncoded));
        Optional.ofNullable(limit()).ifPresent(limit -> builder.add(LIMIT, limit.toString(), urlEncoded));
        Optional.ofNullable(offset()).ifPresent(offset -> builder.add(OFFSET, offset.toString(), urlEncoded));
        Optional.ofNullable(fetchTotal()).ifPresent(withTotal -> builder.add(WITH_TOTAL, withTotal.toString(), urlEncoded));
        expansionPaths().forEach(path -> builder.add(EXPAND, path.toSphereExpand(), urlEncoded));
        additionalHttpQueryParameters().forEach(parameter -> builder.add(parameter.getName(), parameter.getValue(), urlEncoded));
        final String extractedQueryParameters = builder.build();
        if (StringUtils.isEmpty(extractedQueryParameters)) {
            return endpoint;
        } else {
            return endpoint + (endpoint.contains("?") ? "&" : "?" ) + extractedQueryParameters;
        }
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && (o instanceof Query) && ((Query)o).httpRequestIntent().getPath().equals(httpRequestIntent().getPath());
    }

    @Override
    public final int hashCode() {
        return httpRequestIntent().getPath().hashCode();
    }

    @Override
    public String toString() {
        final String readablePath = completePath(endpoint, false);

        return this.getClass().getSimpleName() +"{" +
                "predicate=" + predicate +
                ", sort=" + sort +
                ", expand=" + expansionPaths +
                ", additionalHttpQueryParameters=" + additionalHttpQueryParameters +
                ", limit=" + limit +
                ", offset=" + offset +
                ", endpoint='" + endpoint + '\'' +
                ", resultMapper=" + resultMapper +
                ", readablePath=" + readablePath +
                ", request=" + httpRequestIntent() +
                ", withTotal=" + withTotal +
                '}';
    }

    Q getQueryModel() {
        return queryModel;
    }

    E getExpansionModel() {
        return expansionModel;
    }

    Function<HttpResponse, PagedQueryResult<T>> getResultMapper() {
        return resultMapper;
    }

    protected C withAdditionalHttpQueryParameters(final List<NameValuePair> pairs) {
        return copyBuilder().additionalHttpQueryParameters(pairs).build();
    }

    private static <T> JavaType resolveJavaType(final TypeReference<T> typeReference) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        final JavaType typeParameterJavaType = typeFactory.constructType(typeReference);
        return resolveJavaType(typeParameterJavaType);
    }

    private static <T> JavaType resolveJavaType(final JavaType javaType) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        final JavaType resultJavaType = typeFactory.constructParametricType(PagedQueryResult.class, javaType);
        return resultJavaType;
    }
}
