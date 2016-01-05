package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.utils.ListUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.queries.QueryParameterKeys.*;
import static io.sphere.sdk.utils.ListUtils.listOf;
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
public abstract class MetaModelQueryDslImpl<T, C extends MetaModelQueryDsl<T, C, Q, E>, Q, E> extends SphereRequestBase implements MetaModelQueryDsl<T, C, Q, E> {

    final List<QueryPredicate<T>> predicate;
    final List<QuerySort<T>> sort;
    @Nullable
    final Boolean withTotal;
    @Nullable
    final Long limit;
    @Nullable
    final Long offset;
    final List<ExpansionPath<T>> expansionPaths;
    final List<HttpQueryParameter> additionalQueryParameters;
    final String endpoint;
    final Q queryModel;
    final E expansionModel;
    final Function<HttpResponse, PagedQueryResult<T>> resultMapper;
    final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction;

    public MetaModelQueryDslImpl(final List<QueryPredicate<T>> predicate, final List<QuerySort<T>> sort, @Nullable final Boolean fetchTotal, @Nullable final Long limit,
                                 @Nullable final Long offset, final String endpoint,
                                 final Function<HttpResponse, PagedQueryResult<T>> resultMapper,
                                 final List<ExpansionPath<T>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters,
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
        this.additionalQueryParameters = requireNonNull(additionalQueryParameters);
        this.expansionModel = requireNonNull(expansionModel);
        this.queryModel = requireNonNull(queryModel);
    }

    //uses typeReference of whole result
    public MetaModelQueryDslImpl(final String endpoint, final TypeReference<PagedQueryResult<T>> pagedQueryResultTypeReference,
                                 final Q queryModel, final E expansionModel, final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction,
                                 final List<HttpQueryParameter> additionalQueryParameters) {
        this(emptyList(), emptyList(), null, null, null, endpoint, httpResponse -> deserialize(httpResponse, pagedQueryResultTypeReference),
                emptyList(), additionalQueryParameters, queryModel, expansionModel, queryDslBuilderFunction);
    }

    //uses typeReference of the fetched objects
    public MetaModelQueryDslImpl(final String endpoint, final JavaType singleElementJavatype,
                                 final Q queryModel, final E expansionModel, final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction,
                                 final List<HttpQueryParameter> additionalQueryParameters) {
        this(emptyList(), emptyList(), null, null, null, endpoint, httpResponse -> deserialize(httpResponse, resolveJavaType(singleElementJavatype)),
                emptyList(), additionalQueryParameters, queryModel, expansionModel, queryDslBuilderFunction);
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
                builder.resultMapper, builder.expansionPaths, builder.additionalQueryParameters, builder.queryModel,
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
    public C withPredicates(final Function<Q, QueryPredicate<T>> m) {
        return withPredicates(m.apply(queryModel));
    }

    @Override
    public C plusPredicates(final List<QueryPredicate<T>> predicates) {
        return withPredicates(listOf(predicates(), predicates));
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
        return withSort(ListUtils.listOf(sort(), sort));
    }

    @Override
    public C plusSort(final QuerySort<T> sort) {
        return plusSort(singletonList(sort));
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

    protected List<HttpQueryParameter> additionalQueryParameters() {
        return additionalQueryParameters;
    }

    protected MetaModelQueryDslBuilder<T, C, Q, E> copyBuilder() {
        return new MetaModelQueryDslBuilder<>(this);
    }

    @Override
    public final HttpRequestIntent httpRequestIntent() {
        final String additions = queryParametersToString(true);
        return HttpRequestIntent.of(HttpMethod.GET, endpoint + (additions.length() > 1 ? additions : ""));
    }

    @Override
    public PagedQueryResult<T> deserialize(final HttpResponse httpResponse) {
        return resultMapper.apply(httpResponse);
    }

    private String queryParametersToString(final boolean urlEncoded) {
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        predicates().forEach(predicate -> builder.add(WHERE, predicate.toSphereQuery(), urlEncoded));
        sort().forEach(sort -> builder.add(SORT, sort.toSphereSort(), urlEncoded));
        Optional.ofNullable(limit()).ifPresent(limit -> builder.add(LIMIT, limit.toString(), urlEncoded));
        Optional.ofNullable(offset()).ifPresent(offset -> builder.add(OFFSET, offset.toString(), urlEncoded));
        Optional.ofNullable(fetchTotal()).ifPresent(withTotal -> builder.add(WITH_TOTAL, withTotal.toString(), urlEncoded));
        expansionPaths().forEach(path -> builder.add(EXPAND, path.toSphereExpand(), urlEncoded));
        additionalQueryParameters().forEach(parameter -> builder.add(parameter.getKey(), parameter.getValue(), urlEncoded));
        return builder.toStringWithOptionalQuestionMark();
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
        final String readablePath = endpoint + queryParametersToString(false);

        return this.getClass().getSimpleName() +"{" +
                "predicate=" + predicate +
                ", sort=" + sort +
                ", expand=" + expansionPaths +
                ", additionalQueryParameters=" + additionalQueryParameters +
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

    private static <T> JavaType resolveJavaType(final TypeReference<T> typeReference) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        final JavaType typeParameterJavaType = typeFactory.constructType(typeReference);
        return resolveJavaType(typeParameterJavaType);
    }

    private static <T> JavaType resolveJavaType(final JavaType javaType) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        final JavaType resultJavaType = typeFactory.constructParametrizedType(PagedQueryResult.class, PagedQueryResult.class, javaType);
        return resultJavaType;
    }
}
