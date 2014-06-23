package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import io.sphere.sdk.client.HttpMethod;
import io.sphere.sdk.client.HttpRequest;
import io.sphere.sdk.client.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.UrlQueryBuilder;

import javax.annotation.Nullable;
import java.util.List;

public class QueryDslImpl<I, R extends I, M> implements QueryDsl<I, R, M> {
    static final Sort SORT_BY_ID = new Sort() {
        @Override
        public String toSphereSort() {
            return "id asc";
        }
    };
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
    private final TypeReference<PagedQueryResult<R>> typeReference;

    public QueryDslImpl(Optional<Predicate<M>> predicate, List<Sort> sort, Optional<Long> limit, Optional<Long> offset, String endpoint, TypeReference<PagedQueryResult<R>> typeReference) {
        this.predicate = predicate;
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
        this.endpoint = endpoint;
        this.typeReference = typeReference;
    }

    public QueryDslImpl(String endpoint, TypeReference<PagedQueryResult<R>> typeReference) {
        this(Optional.<Predicate<M>>absent(), SORT_BY_ID_LIST, Optional.<Long>absent(), Optional.<Long>absent(), endpoint, typeReference);
    }

    @Override
    public QueryDsl<I, R, M> withPredicate(final Predicate<M> predicate) {
        return copyBuilder().predicate(predicate).build();
    }

    private EntityQueryBuilder<I, R, M> copyBuilder() {
        return new EntityQueryBuilder<I,R,M>(this);
    }

    @Override
    public QueryDsl<I, R, M> withSort(final List<Sort> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public QueryDsl<I, R, M> withLimit(final long limit) {
        return copyBuilder().limit(limit).build();
    }

    @Override
    public QueryDsl<I, R, M> withOffset(final long offset) {
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

    @Override
    public TypeReference<PagedQueryResult<R>> typeReference() {
        return typeReference;
    }

    @Override
    public Function<HttpResponse, PagedQueryResult<I>> resultMapper() {
        return new Function<HttpResponse, PagedQueryResult<I>>() {
            @Override
            public PagedQueryResult<I> apply(final HttpResponse httpResponse) {
                final PagedQueryResult<R> intermediateResult = JsonUtils.readObjectFromJsonString(typeReference(), httpResponse.getResponseBody());
                final List<I> casted = Lists.transform(intermediateResult.getResults(), new Function<R, I>() {
                    @Override
                    public I apply(final R input) {
                        return input;
                    }
                });
                return PagedQueryResult.of(intermediateResult.getOffset(), intermediateResult.getTotal(), casted);
            }
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
                ", typeReference=" + typeReference +
                ", readablePath=" + readablePath +
                '}';
    }
}
