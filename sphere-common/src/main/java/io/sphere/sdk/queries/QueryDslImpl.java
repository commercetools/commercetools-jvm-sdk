package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.Optional;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.UrlQueryBuilder;
import static io.sphere.sdk.queries.QueryParameterKeys.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;


import java.util.List;

class QueryDslImpl<T> extends SphereRequestBase implements QueryDsl<T> {

    private final Optional<QueryPredicate<T>> predicate;
    private final List<QuerySort<T>> sort;
    private final Optional<Long> limit;
    private final Optional<Long> offset;
    private final List<ExpansionPath<T>> expansionPaths;
    private final List<QueryParameter> additionalQueryParameters;
    private final String endpoint;
    private final Function<HttpResponse, PagedQueryResult<T>> resultMapper;

    public QueryDslImpl(final Optional<QueryPredicate<T>> predicate, final List<QuerySort<T>> sort, final Optional<Long> limit,
                        final Optional<Long> offset, final String endpoint,
                        final Function<HttpResponse, PagedQueryResult<T>> resultMapper,
                        final List<ExpansionPath<T>> expansionPaths, final List<QueryParameter> additionalQueryParameters) {
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
    }

    public QueryDslImpl(final String endpoint, final List<QueryParameter> additionalQueryParameters, final Function<HttpResponse, PagedQueryResult<T>> resultMapper) {
        this(Optional.<QueryPredicate<T>>empty(), sortByIdList(), Optional.<Long>empty(), Optional.<Long>empty(), endpoint, resultMapper, Collections.<ExpansionPath<T>>emptyList(), additionalQueryParameters);
    }

    public QueryDslImpl(final String endpoint, final Function<HttpResponse, PagedQueryResult<T>> resultMapper) {
        this(Optional.<QueryPredicate<T>>empty(), sortByIdList(), Optional.<Long>empty(), Optional.<Long>empty(), endpoint, resultMapper, Collections.<ExpansionPath<T>>emptyList(), Collections.emptyList());
    }

    public QueryDslImpl(final String endpoint, final List<QueryParameter> additionalQueryParameters, final TypeReference<PagedQueryResult<T>> pagedQueryResultTypeReference) {
        this(endpoint, additionalQueryParameters, QueryDslImpl.resultMapperOf(pagedQueryResultTypeReference));
    }

    public QueryDslImpl(final String endpoint, final TypeReference<PagedQueryResult<T>> pagedQueryResultTypeReference) {
        this(endpoint, Collections.emptyList(), pagedQueryResultTypeReference);
    }

    @Override
    public QueryDsl<T> withPredicate(final QueryPredicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return copyBuilder().predicate(predicate).build();
    }

    private QueryDslBuilder<T> copyBuilder() {
        return new QueryDslBuilder<>(this);
    }

    @Override
    public QueryDsl<T> withSort(final List<QuerySort<T>> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public QueryDsl<T> withLimit(final long limit) {
        return copyBuilder().limit(limit).build();
    }

    @Override
    public QueryDsl<T> withOffset(final long offset) {
        return copyBuilder().offset(offset).build();
    }

    @Override
    public QueryDsl<T> withExpansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return copyBuilder().expansionPaths(expansionPaths).build();
    }

    @Override
    public QueryDsl<T> withAdditionalQueryParameters(final List<QueryParameter> additionalQueryParameters) {
        return copyBuilder().additionalQueryParameters(additionalQueryParameters).build();
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
    public List<QueryParameter> additionalQueryParameters() {
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
        return "?" + builder.toString();
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
}
