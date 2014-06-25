package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import io.sphere.sdk.requests.HttpMethod;
import io.sphere.sdk.requests.HttpRequest;
import io.sphere.sdk.requests.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.UrlQueryBuilder;

import java.util.List;

public class QueryDslImpl<I, R extends I, M> implements QueryDsl<I, M> {
    static final Sort SORT_BY_ID = () -> "id asc";
    static final List<Sort> SORT_BY_ID_LIST = Lists.newArrayList(SORT_BY_ID);
    private static final String WHERE = "where";
    private static final String SORT = "sort";
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";
    private final Optional<Predicate<M>> predicate;
    private final List<Sort> sort;
    private final Optional<Long> limit;
    private final Optional<Long> offset;
    private final String endpoint;
    private final Function<HttpResponse, PagedQueryResult<I>> resultMapper;

    public QueryDslImpl(final Optional<Predicate<M>> predicate, final List<Sort> sort, final Optional<Long> limit,
                        final Optional<Long> offset, final String endpoint,
                        final Function<HttpResponse, PagedQueryResult<I>> resultMapper) {
        this.predicate = predicate;
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
    }

    public QueryDslImpl(final String endpoint, final Function<HttpResponse, PagedQueryResult<I>> resultMapper) {
        this(Optional.<Predicate<M>>absent(), SORT_BY_ID_LIST, Optional.<Long>absent(), Optional.<Long>absent(), endpoint, resultMapper);
    }

    public QueryDslImpl(final String endpoint, final TypeReference<PagedQueryResult<R>> pagedQueryResultTypeReference) {
        this(endpoint, QueryDslImpl.<I, R>resultMapperOf(pagedQueryResultTypeReference));
    }

    @Override
    public QueryDsl<I, M> withPredicate(final Predicate<M> predicate) {
        return copyBuilder().predicate(predicate).build();
    }

    private EntityQueryBuilder<I, R, M> copyBuilder() {
        return new EntityQueryBuilder<>(this);
    }

    @Override
    public QueryDsl<I, M> withSort(final List<Sort> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public QueryDsl<I, M> withLimit(final long limit) {
        return copyBuilder().limit(limit).build();
    }

    @Override
    public QueryDsl<I, M> withOffset(final long offset) {
        return copyBuilder().offset(offset).build();
    }

    @Override
    public Optional<Predicate<M>> predicate() {
        return predicate;
    }

    @Override
    public List<Sort> sort() {
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
        if (predicate().isPresent()) {
            builder.add(WHERE, predicate().get().toSphereQuery(), urlEncoded);
        }
        for (final Sort sort : sort()) {
            builder.add(SORT, sort.toSphereSort(), urlEncoded);
        }
        if (limit().isPresent()) {
            builder.add(LIMIT, limit().get().toString(), urlEncoded);
        }
        if (offset().isPresent()) {
            builder.add(OFFSET, offset().get().toString(), urlEncoded);
        }
        return "?" + builder.toString();
    }

    //TODO check visibility and class location
    public static <A, B extends A> Function<HttpResponse, PagedQueryResult<A>> resultMapperOf(final TypeReference<PagedQueryResult<B>> pagedQueryResultTypeReference) {
        return httpResponse -> {
            final PagedQueryResult<B> intermediateResult = JsonUtils.readObjectFromJsonString(pagedQueryResultTypeReference, httpResponse.getResponseBody());
            //TODO use function identity in Java 8, call is necessary since List<B extends A> is not a subclass of List<A>
            final List<A> casted = Lists.transform(intermediateResult.getResults(), input -> input);
            return PagedQueryResult.of(intermediateResult.getOffset(), intermediateResult.getTotal(), casted);
        };
    }

    @Override
    public String toString() {
        final String readablePath = endpoint + queryParametersToString(false);

        return "EntityQueryWithCopyImpl{" +
                "predicate=" + predicate +
                ", sort=" + sort +
                ", limit=" + limit +
                ", offset=" + offset +
                ", endpoint='" + endpoint + '\'' +
                ", resultMapper=" + resultMapper +
                ", readablePath=" + readablePath +
                '}';
    }
}
