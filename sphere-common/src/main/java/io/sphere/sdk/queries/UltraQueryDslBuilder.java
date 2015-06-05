package io.sphere.sdk.queries;

import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class UltraQueryDslBuilder<T, C extends UltraQueryDsl<T, C, Q, E>, Q, E> extends Base implements Builder<C> {

    protected Optional<QueryPredicate<T>> predicate = Optional.empty();
    protected List<QuerySort<T>> sort = sortByIdList();
    protected Optional<Long> limit = Optional.empty();
    protected Optional<Long> offset = Optional.empty();
    protected List<ExpansionPath<T>> expansionPaths = Collections.emptyList();
    protected List<HttpQueryParameter> additionalQueryParameters = Collections.emptyList();
    protected final String endpoint;
    protected final Function<HttpResponse, PagedQueryResult<T>> resultMapper;
    protected final Q queryModel;
    protected final E expansionModel;
    protected final Function<UltraQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction;


    public UltraQueryDslBuilder(final String endpoint, final Function<HttpResponse, PagedQueryResult<T>> resultMapper, final Q queryModel, final E expansionModel, final Function<UltraQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction) {
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
        this.queryModel = queryModel;
        this.expansionModel = expansionModel;
        this.queryDslBuilderFunction = queryDslBuilderFunction;
    }

    public UltraQueryDslBuilder(final UltraQueryDslImpl<T, C, Q, E> template) {
        this(template.endpoint(), r -> template.deserialize(r), template.getQueryModel(), template.getExpansionModel(), template.queryDslBuilderFunction);
        predicate = template.predicate();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
        expansionPaths = template.expansionPaths();
        additionalQueryParameters = template.additionalQueryParameters();
    }

    public UltraQueryDslBuilder<T, C, Q, E> predicate(final Optional<QueryPredicate<T>> predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public UltraQueryDslBuilder<T, C, Q, E> predicate(final QueryPredicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return predicate(Optional.of(predicate));
    }
    
    public UltraQueryDslBuilder<T, C, Q, E> sort(final List<QuerySort<T>> sort) {
        this.sort = sort;
        return this;
    }

    public UltraQueryDslBuilder<T, C, Q, E> limit(final Optional<Long> limit) {
        this.limit = limit;
        return this;
    }

    public UltraQueryDslBuilder<T, C, Q, E> limit(final long limit) {
        return limit(Optional.of(limit));
    }

    public UltraQueryDslBuilder<T, C, Q, E> offset(final Optional<Long> offset) {
        this.offset = offset;
        return this;
    }

    public UltraQueryDslBuilder<T, C, Q, E> offset(final long offset) {
        return offset(Optional.of(offset));
    }

    public UltraQueryDslBuilder<T, C, Q, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public UltraQueryDslBuilder<T, C, Q, E> additionalQueryParameters(final List<HttpQueryParameter> additionalQueryParameters) {
        this.additionalQueryParameters = additionalQueryParameters;
        return this;
    }

    @Override
    public C build() {
        return queryDslBuilderFunction.apply(this);
    }

    static <T> List<QuerySort<T>> sortByIdList() {
        final QuerySort<T> sortById = QuerySort.<T>of("id asc");
        return asList(sortById);
    }
}
