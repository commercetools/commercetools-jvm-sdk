package io.sphere.sdk.queries;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.sphere.sdk.requests.HttpResponse;

import java.util.List;

import static io.sphere.sdk.queries.QueryDslImpl.*;

public class EntityQueryBuilder<I, M> {

    private Optional<Predicate<M>> predicate = Optional.absent();
    private List<Sort> sort = SORT_BY_ID_LIST;
    private Optional<Long> limit = Optional.absent();
    private Optional<Long> offset = Optional.absent();
    private final String endpoint;
    private final Function<HttpResponse, PagedQueryResult<I>> resultMapper;


    public EntityQueryBuilder(final String endpoint, final Function<HttpResponse, PagedQueryResult<I>> resultMapper) {
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
    }

    public EntityQueryBuilder(final QueryDsl<I, M> template) {
        this(template.endpoint(), template.resultMapper());
        predicate = template.predicate();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
    }

    public EntityQueryBuilder<I, M> predicate(final Optional<Predicate<M>> predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public EntityQueryBuilder<I, M> predicate(final Predicate<M> predicate) {
        return predicate(Optional.fromNullable(predicate));
    }
    
    public EntityQueryBuilder<I, M> sort(final List<Sort> sort) {
        this.sort = sort;
        return this;
    }

    public EntityQueryBuilder<I, M> limit(final Optional<Long> limit) {
        this.limit = limit;
        return this;
    }

    public EntityQueryBuilder<I, M> limit(final long limit) {
        return limit(Optional.fromNullable(limit));
    }

    public EntityQueryBuilder<I, M> offset(final Optional<Long> offset) {
        this.offset = offset;
        return this;
    }

    public EntityQueryBuilder<I, M> offset(final long offset) {
        return offset(Optional.fromNullable(offset));
    }

    public QueryDsl<I, M> build() {
        return new QueryDslImpl<>(predicate, sort, limit, offset, endpoint, resultMapper);
    }
}
