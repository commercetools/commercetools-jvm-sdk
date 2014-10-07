package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.Optional;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.UrlQueryBuilder;
import static io.sphere.sdk.queries.QueryParameterKeys.*;
import static java.util.Arrays.asList;


import java.util.List;

class QueryDslImpl<I> implements QueryDsl<I> {

    private final Optional<Predicate<I>> predicate;
    private final List<Sort<I>> sort;
    private final Optional<Long> limit;
    private final Optional<Long> offset;
    private final List<ExpansionPath<I>> expansionPaths;
    private final List<QueryParameter> additionalQueryParameters;
    private final String endpoint;
    private final Function<HttpResponse, PagedQueryResult<I>> resultMapper;

    public QueryDslImpl(final Optional<Predicate<I>> predicate, final List<Sort<I>> sort, final Optional<Long> limit,
                        final Optional<Long> offset, final String endpoint,
                        final Function<HttpResponse, PagedQueryResult<I>> resultMapper,
                        final List<ExpansionPath<I>> expansionPaths, final List<QueryParameter> additionalQueryParameters) {
        this.predicate = predicate;
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
        this.expansionPaths = expansionPaths;
        this.additionalQueryParameters = additionalQueryParameters;
    }

    public QueryDslImpl(final String endpoint, final List<QueryParameter> additionalQueryParameters, final Function<HttpResponse, PagedQueryResult<I>> resultMapper) {
        this(Optional.<Predicate<I>>empty(), sortByIdList(), Optional.<Long>empty(), Optional.<Long>empty(), endpoint, resultMapper, Collections.<ExpansionPath<I>>emptyList(), additionalQueryParameters);
    }

    public QueryDslImpl(final String endpoint, final Function<HttpResponse, PagedQueryResult<I>> resultMapper) {
        this(Optional.<Predicate<I>>empty(), sortByIdList(), Optional.<Long>empty(), Optional.<Long>empty(), endpoint, resultMapper, Collections.<ExpansionPath<I>>emptyList(), Collections.emptyList());
    }

    public QueryDslImpl(final String endpoint, final List<QueryParameter> additionalQueryParameters, final TypeReference<PagedQueryResult<I>> pagedQueryResultTypeReference) {
        this(endpoint, additionalQueryParameters, QueryDslImpl.resultMapperOf(pagedQueryResultTypeReference));
    }

    public QueryDslImpl(final String endpoint, final TypeReference<PagedQueryResult<I>> pagedQueryResultTypeReference) {
        this(endpoint, Collections.emptyList(), pagedQueryResultTypeReference);
    }

    @Override
    public QueryDsl<I> withPredicate(final Predicate<I> predicate) {
        Objects.requireNonNull(predicate);
        return copyBuilder().predicate(predicate).build();
    }

    private EntityQueryBuilder<I> copyBuilder() {
        return new EntityQueryBuilder<>(this);
    }

    @Override
    public QueryDsl<I> withSort(final List<Sort<I>> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public QueryDsl<I> withLimit(final long limit) {
        return copyBuilder().limit(limit).build();
    }

    @Override
    public QueryDsl<I> withOffset(final long offset) {
        return copyBuilder().offset(offset).build();
    }

    @Override
    public QueryDsl<I> withExpansionPaths(final List<ExpansionPath<I>> expansionPaths) {
        return copyBuilder().expansionPaths(expansionPaths).build();
    }

    @Override
    public QueryDsl<I> withAdditionalQueryParameters(final List<QueryParameter> additionalQueryParameters) {
        return copyBuilder().additionalQueryParameters(additionalQueryParameters).build();
    }

    @Override
    public Optional<Predicate<I>> predicate() {
        return predicate;
    }

    @Override
    public List<Sort<I>> sort() {
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
    public List<ExpansionPath<I>> expansionPaths() {
        return expansionPaths;
    }

    @Override
    public List<QueryParameter> additionalQueryParameters() {
        return additionalQueryParameters;
    }

    @Override
    public final HttpRequest httpRequest() {
        final String additions = queryParametersToString(true);
        return HttpRequest.of(HttpMethod.GET, endpoint + (additions.length() > 1 ? additions : ""));
    }

    @Override
    public Function<HttpResponse, PagedQueryResult<I>> resultMapper() {
        return resultMapper;
    }

    private String queryParametersToString(final boolean urlEncoded) {
        final UrlQueryBuilder builder = new UrlQueryBuilder();
        predicate().ifPresent(predicate -> builder.add(WHERE, predicate.toSphereQuery(), urlEncoded));
        sort().forEach(sort -> builder.add(SORT, sort.toSphereSort(), urlEncoded));
        limit().ifPresent(limit -> builder.add(LIMIT, limit.toString(), urlEncoded));
        offset().ifPresent(offset -> builder.add(OFFSET, offset.toString(), urlEncoded));
        expansionPaths().forEach(path -> builder.add(EXPAND, path.toSphereExpand(), urlEncoded));
        additionalQueryParameters().forEach(parameter -> builder.add(parameter.getKey(), parameter.getValue(), urlEncoded));
        return "?" + builder.toString();
    }

    //TODO check visibility and class location
    public static <A> Function<HttpResponse, PagedQueryResult<A>> resultMapperOf(final TypeReference<PagedQueryResult<A>> pagedQueryResultTypeReference) {
        return httpResponse -> JsonUtils.readObjectFromJsonString(pagedQueryResultTypeReference, httpResponse.getResponseBody());
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
                '}';
    }

    static <I> List<Sort<I>> sortByIdList() {
        final Sort<I> sortById = Sort.<I>of("id asc");
        return asList(sortById);
    }
}
