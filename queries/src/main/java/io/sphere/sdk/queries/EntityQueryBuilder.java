package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import io.sphere.sdk.client.PagedQueryResult;

import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.queries.EntityQueryWithCopyImpl.*;

public class EntityQueryBuilder<I,R,M> {

    private Optional<Predicate<M>> predicate = Optional.absent();
    private List<Sort> sort = SORT_BY_ID_LIST;
    private Optional<Long> limit = Optional.absent();
    private Optional<Long> offset = Optional.absent();
    private final String endpoint;
    private final TypeReference<PagedQueryResult<R>> typeReference;


    public EntityQueryBuilder(String endpoint, TypeReference<PagedQueryResult<R>> typeReference) {
        this.endpoint = endpoint;
        this.typeReference = typeReference;
    }

    public EntityQueryBuilder(final EntityQueryWithCopy<I,R,M> template) {
        this(template.endpoint(), template.typeReference());
        predicate = template.predicate();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
    }

    public EntityQueryBuilder<I,R,M> predicate(final Optional<Predicate<M>> predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public EntityQueryBuilder<I,R,M> predicate(final Predicate<M> predicate) {
        return predicate(Optional.fromNullable(predicate));
    }
    
    public EntityQueryBuilder<I,R,M> sort(final List<Sort> sort) {
        this.sort = sort;
        return this;
    }

    public EntityQueryBuilder<I,R,M> limit(final Optional<Long> limit) {
        this.limit = limit;
        return this;
    }

    public EntityQueryBuilder<I,R,M> limit(final long limit) {
        return limit(Optional.fromNullable(limit));
    }

    public EntityQueryBuilder<I,R,M> offset(final Optional<Long> offset) {
        this.offset = offset;
        return this;
    }

    public EntityQueryBuilder<I,R,M> offset(final long offset) {
        return offset(Optional.fromNullable(offset));
    }

    public EntityQueryWithCopy<I, R, M> build() {
        return new EntityQueryWithCopyImpl<I, R, M>(predicate, sort, limit, offset, endpoint, typeReference);
    }
}
