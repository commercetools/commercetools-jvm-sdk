package io.sphere.sdk.queries;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.queries.QueryDslImpl.*;

class QueryDslBuilder<I> extends Base implements Builder<QueryDsl<I>> {

    private Optional<Predicate<I>> predicate = Optional.empty();
    private List<QuerySort<I>> sort = sortByIdList();
    private Optional<Long> limit = Optional.empty();
    private Optional<Long> offset = Optional.empty();
    private List<ExpansionPath<I>> expansionPaths = Collections.emptyList();
    private List<QueryParameter> additionalQueryParameters = Collections.emptyList();
    private final String endpoint;
    private final Function<HttpResponse, PagedQueryResult<I>> resultMapper;


    public QueryDslBuilder(final String endpoint, final Function<HttpResponse, PagedQueryResult<I>> resultMapper) {
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
    }

    public QueryDslBuilder(final QueryDsl<I> template) {
        this(template.endpoint(), r -> template.deserialize(r));
        predicate = template.predicate();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
        expansionPaths = template.expansionPaths();
        additionalQueryParameters = template.additionalQueryParameters();
    }

    public QueryDslBuilder<I> predicate(final Optional<Predicate<I>> predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public QueryDslBuilder<I> predicate(final Predicate<I> predicate) {
        Objects.requireNonNull(predicate);
        return predicate(Optional.of(predicate));
    }
    
    public QueryDslBuilder<I> sort(final List<QuerySort<I>> sort) {
        this.sort = sort;
        return this;
    }

    public QueryDslBuilder<I> limit(final Optional<Long> limit) {
        this.limit = limit;
        return this;
    }

    public QueryDslBuilder<I> limit(final long limit) {
        return limit(Optional.of(limit));
    }

    public QueryDslBuilder<I> offset(final Optional<Long> offset) {
        this.offset = offset;
        return this;
    }

    public QueryDslBuilder<I> offset(final long offset) {
        return offset(Optional.of(offset));
    }

    public QueryDslBuilder<I> expansionPaths(final List<ExpansionPath<I>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public QueryDslBuilder<I> additionalQueryParameters(final List<QueryParameter> additionalQueryParameters) {
        this.additionalQueryParameters = additionalQueryParameters;
        return this;
    }

    @Override
    public QueryDsl<I> build() {
        return new QueryDslImpl<>(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters);
    }
}
