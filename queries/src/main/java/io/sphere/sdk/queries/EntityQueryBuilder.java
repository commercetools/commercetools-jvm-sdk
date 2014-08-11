package io.sphere.sdk.queries;

import java.util.Collections;
import java.util.function.Function;
import java.util.Optional;
import io.sphere.sdk.requests.HttpResponse;

import java.util.List;

import static io.sphere.sdk.queries.QueryDslImpl.*;

public class EntityQueryBuilder<I> {

    private Optional<Predicate<I>> predicate = Optional.empty();
    private List<Sort<I>> sort = sortByIdList();
    private Optional<Long> limit = Optional.empty();
    private Optional<Long> offset = Optional.empty();
    private List<ExpansionPath<I>> expansionPaths = Collections.emptyList();
    private final String endpoint;
    private final Function<HttpResponse, PagedQueryResult<I>> resultMapper;


    public EntityQueryBuilder(final String endpoint, final Function<HttpResponse, PagedQueryResult<I>> resultMapper) {
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
    }

    public EntityQueryBuilder(final QueryDsl<I> template) {
        this(template.endpoint(), template.resultMapper());
        predicate = template.predicate();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
        expansionPaths = template.expansionPaths();
    }

    public EntityQueryBuilder<I> predicate(final Optional<Predicate<I>> predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public EntityQueryBuilder<I> predicate(final Predicate<I> predicate) {
        return predicate(Optional.ofNullable(predicate));
    }
    
    public EntityQueryBuilder<I> sort(final List<Sort<I>> sort) {
        this.sort = sort;
        return this;
    }

    public EntityQueryBuilder<I> limit(final Optional<Long> limit) {
        this.limit = limit;
        return this;
    }

    public EntityQueryBuilder<I> limit(final long limit) {
        return limit(Optional.ofNullable(limit));
    }

    public EntityQueryBuilder<I> offset(final Optional<Long> offset) {
        this.offset = offset;
        return this;
    }

    public EntityQueryBuilder<I> offset(final long offset) {
        return offset(Optional.ofNullable(offset));
    }

    public EntityQueryBuilder<I> expansionPaths(final List<ExpansionPath<I>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public QueryDsl<I> build() {
        return new QueryDslImpl<>(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths);
    }
}
