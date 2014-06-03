package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import io.sphere.sdk.client.HttpMethod;
import io.sphere.sdk.client.HttpRequest;
import io.sphere.sdk.client.PagedQueryResult;
import io.sphere.sdk.utils.UrlQueryBuilder;

import java.util.List;

public class EntityQueryWithCopyImpl<I, R, M> implements EntityQueryWithCopy<I, R, M> {
    static final Sort SORT_BY_ID = new Sort() {
        @Override
        public String toSphereSort() {
            return "id asc";
        }
    };
    static final List<Sort> SORT_BY_ID_LIST = Lists.newArrayList(SORT_BY_ID);
    private final Optional<Predicate<M>> predicate;
    private final List<Sort> sort;
    private final Optional<Long> limit;
    private final Optional<Long> offset;
    private final String endpoint;
    private final TypeReference<PagedQueryResult<R>> typeReference;

    public EntityQueryWithCopyImpl(Optional<Predicate<M>> predicate, List<Sort> sort, Optional<Long> limit, Optional<Long> offset, String endpoint, TypeReference<PagedQueryResult<R>> typeReference) {
        this.predicate = predicate;
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
        this.endpoint = endpoint;
        this.typeReference = typeReference;
    }

    public EntityQueryWithCopyImpl(String endpoint, TypeReference<PagedQueryResult<R>> typeReference) {
        this(Optional.<Predicate<M>>absent(), SORT_BY_ID_LIST, Optional.<Long>absent(), Optional.<Long>absent(), endpoint, typeReference);
    }

    @Override
    public EntityQueryWithCopy<I, R, M> withPredicate(final Predicate<M> predicate) {
        return copyBuilder().predicate(predicate).build();
    }

    private EntityQueryBuilder<I, R, M> copyBuilder() {
        return new EntityQueryBuilder<I,R,M>(this);
    }

    @Override
    public EntityQueryWithCopy<I, R, M> withSort(final List<Sort> sort) {
        return copyBuilder().sort(sort).build();
    }

    @Override
    public EntityQueryWithCopy<I, R, M> withLimit(final long limit) {
        return copyBuilder().limit(limit).build();
    }

    @Override
    public EntityQueryWithCopy<I, R, M> withOffset(final long offset) {
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
        final UrlQueryBuilder builder = new UrlQueryBuilder();
        if (predicate().isPresent()) {
            builder.addEncoded("where", predicate().get().toSphereQuery());
        }
        for (final Sort sort : sort()) {
            builder.addEncoded("sort", sort.toSphereSort());
        }
        if (limit().isPresent()) {
            builder.addEncoded("limit", limit().get().toString());
        }
        if (offset().isPresent()) {
            builder.addEncoded("offset", offset().get().toString());
        }
        final String additions = "?" + builder.toString();
        return HttpRequest.of(HttpMethod.GET, endpoint + (additions.length() > 1 ? additions : ""));
    }

    @Override
    public TypeReference<PagedQueryResult<R>> typeReference() {
        return typeReference;
    }
}
