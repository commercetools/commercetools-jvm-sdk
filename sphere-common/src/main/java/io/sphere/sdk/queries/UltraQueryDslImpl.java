package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.UrlQueryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.queries.QueryParameterKeys.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;

public abstract class UltraQueryDslImpl<T, C extends UltraQueryDsl<T, C, Q, E>, Q, E> extends SphereRequestBase implements UltraQueryDsl<T, C, Q, E> {

    private final Q queryModel;
    private final E expansionModel;
    private final Optional<QueryPredicate<T>> predicate;
    private final List<QuerySort<T>> sort;
    private final Optional<Long> limit;
    private final Optional<Long> offset;
    private final List<ExpansionPath<T>> expansionPaths;
    private final List<HttpQueryParameter> additionalQueryParameters;
    private final String endpoint;
    private final Function<HttpResponse, PagedQueryResult<T>> resultMapper;

    public UltraQueryDslImpl(final Optional<QueryPredicate<T>> predicate, final List<QuerySort<T>> sort, final Optional<Long> limit,
                             final Optional<Long> offset, final String endpoint,
                             final Function<HttpResponse, PagedQueryResult<T>> resultMapper,
                             final List<ExpansionPath<T>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters,
                             final Q queryModel, final E expansionModel) {
        offset.ifPresent(presentOffset -> {
            if (presentOffset < MIN_OFFSET || presentOffset > MAX_OFFSET) {
                throw new IllegalArgumentException(format("The offset parameter must be in the range of [%d..%d], but was %d.", MIN_OFFSET, MAX_OFFSET, presentOffset));
            }
        });
        this.predicate = predicate;
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
        this.expansionPaths = expansionPaths;
        this.additionalQueryParameters = additionalQueryParameters;
        this.expansionModel = expansionModel;
        this.queryModel = queryModel;
    }

    public UltraQueryDslImpl(final String endpoint, final List<HttpQueryParameter> additionalQueryParameters, final TypeReference<PagedQueryResult<T>> pagedQueryResultTypeReference, final Q queryModel, final E expansionModel) {
        this(Optional.<QueryPredicate<T>>empty(), sortByIdList(), Optional.<Long>empty(), Optional.<Long>empty(), endpoint, resultMapperOf(pagedQueryResultTypeReference),
                Collections.emptyList(), additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    public C withPredicate(final QueryPredicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return copyBuilder().predicate(predicate).build();
    }

    @Override
    public C withPredicate(final Function<Q, QueryPredicate<T>> m) {
        return withPredicate(m.apply(queryModel));
    }

    protected abstract UltraQueryDslBuilder<T, C, Q, E> copyBuilder();

    @Override
    public C withSort(final List<QuerySort<T>> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public C withSort(final Function<Q, QuerySort<T>> m) {
        return withSort(asList(m.apply(queryModel)));
    }

    @Override
    public C withSortMulti(final Function<Q, List<QuerySort<T>>> m) {
        return withSort(m.apply(queryModel));
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
    public C withExpansionPath(final List<ExpansionPath<T>> expansionPaths) {
        return copyBuilder().expansionPaths(expansionPaths).build();
    }

    @Override
    public C withAdditionalQueryParameters(final List<HttpQueryParameter> additionalQueryParameters) {
        return copyBuilder().additionalQueryParameters(additionalQueryParameters).build();
    }

    @Override
    public C plusExpansionPath(final Function<E, ExpansionPath<T>> m) {
        return plusExpansionPath(m.apply(expansionModel));
    }

    @Override
    public C withExpansionPath(final Function<E, ExpansionPath<T>> m) {
        return withExpansionPath(asList(m.apply(expansionModel)));
    }

    @Override
    public Optional<QueryPredicate<T>> predicate() {
        return predicate;
    }

    @Override
    public List<QuerySort<T>> sort() {
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
    public String endpoint() {
        return endpoint;
    }

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return expansionPaths;
    }

    @Override
    public List<HttpQueryParameter> additionalQueryParameters() {
        return additionalQueryParameters;
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
        predicate().ifPresent(predicate -> builder.add(WHERE, predicate.toSphereQuery(), urlEncoded));
        sort().forEach(sort -> builder.add(SORT, sort.toSphereSort(), urlEncoded));
        limit().ifPresent(limit -> builder.add(LIMIT, limit.toString(), urlEncoded));
        offset().ifPresent(offset -> builder.add(OFFSET, offset.toString(), urlEncoded));
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

        return "QueryDslImpl{" +
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
                '}';
    }

    static <T> List<QuerySort<T>> sortByIdList() {
        final QuerySort<T> sortById = QuerySort.<T>of("id asc");
        return asList(sortById);
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
}
