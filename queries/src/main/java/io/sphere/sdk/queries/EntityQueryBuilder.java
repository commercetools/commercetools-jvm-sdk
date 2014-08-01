package io.sphere.sdk.queries;

import java.util.Collections;
import java.util.function.Function;
import java.util.Optional;
import io.sphere.sdk.requests.HttpResponse;

import java.util.List;

import static io.sphere.sdk.queries.QueryDslImpl.*;

public class EntityQueryBuilder<I, M> {

    private Optional<Predicate<M>> predicate = Optional.empty();
    private List<Sort> sort = SORT_BY_ID_LIST;
    private Optional<Long> limit = Optional.empty();
    private Optional<Long> offset = Optional.empty();
    private List<ExpansionPath> expansionPaths = Collections.emptyList();
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
        expansionPaths = template.expansionPaths();
    }

    public EntityQueryBuilder<I, M> predicate(final Optional<Predicate<M>> predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public EntityQueryBuilder<I, M> predicate(final Predicate<M> predicate) {
        return predicate(Optional.ofNullable(predicate));
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
        return limit(Optional.ofNullable(limit));
    }

    public EntityQueryBuilder<I, M> offset(final Optional<Long> offset) {
        this.offset = offset;
        return this;
    }

    public EntityQueryBuilder<I, M> offset(final long offset) {
        return offset(Optional.ofNullable(offset));
    }

    public EntityQueryBuilder<I, M> expansionPaths(final List<ExpansionPath> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public QueryDsl<I, M> build() {
        return new QueryDslImpl<>(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths);
    }
}
