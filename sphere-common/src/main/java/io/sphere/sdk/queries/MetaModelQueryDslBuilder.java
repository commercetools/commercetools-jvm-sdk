package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 * @param <Q> type of the query model
 * @param <E> type of the expansion model
 */
public final class MetaModelQueryDslBuilder<T, C extends MetaModelQueryDsl<T, C, Q, E>, Q, E> extends Base implements Builder<C> {

    protected List<QueryPredicate<T>> predicate = Collections.emptyList();
    protected List<QuerySort<T>> sort = Collections.emptyList();
    @Nullable
    protected Boolean withTotal;
    @Nullable
    protected Long limit;
    @Nullable
    protected Long offset;
    protected List<ExpansionPath<T>> expansionPaths = Collections.emptyList();
    protected List<NameValuePair> additionalHttpQueryParameters = Collections.emptyList();
    protected final String endpoint;
    protected final Function<HttpResponse, PagedQueryResult<T>> resultMapper;
    protected final Q queryModel;
    protected final E expansionModel;
    protected final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction;


    MetaModelQueryDslBuilder(final String endpoint, final Function<HttpResponse, PagedQueryResult<T>> resultMapper, final Q queryModel, final E expansionModel, final Function<MetaModelQueryDslBuilder<T, C, Q, E>, C> queryDslBuilderFunction) {
        this.endpoint = endpoint;
        this.resultMapper = resultMapper;
        this.queryModel = queryModel;
        this.expansionModel = expansionModel;
        this.queryDslBuilderFunction = queryDslBuilderFunction;
    }

    protected MetaModelQueryDslBuilder(final MetaModelQueryDslImpl<T, C, Q, E> template) {
        this(template.endpoint(), r -> template.deserialize(r), template.getQueryModel(), template.getExpansionModel(), template.queryDslBuilderFunction);
        predicate = template.predicates();
        sort = template.sort();
        limit = template.limit();
        offset = template.offset();
        expansionPaths = template.expansionPaths();
        additionalHttpQueryParameters = template.additionalHttpQueryParameters();
        withTotal = template.fetchTotal();
    }

    public MetaModelQueryDslBuilder<T, C, Q, E> predicates(final List<QueryPredicate<T>> predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public MetaModelQueryDslBuilder<T, C, Q, E> sort(final List<QuerySort<T>> sort) {
        this.sort = sort;
        return this;
    }

    public MetaModelQueryDslBuilder<T, C, Q, E> limit(@Nullable final Long limit) {
        this.limit = limit;
        return this;
    }

    public MetaModelQueryDslBuilder<T, C, Q, E> fetchTotal(@Nullable final Boolean fetchTotal) {
        this.withTotal = fetchTotal;
        return this;
    }

    public MetaModelQueryDslBuilder<T, C, Q, E> offset(@Nullable final Long offset) {
        this.offset = offset;
        return this;
    }

    public MetaModelQueryDslBuilder<T, C, Q, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public MetaModelQueryDslBuilder<T, C, Q, E> additionalHttpQueryParameters(final List<NameValuePair> additionalQueryParameters) {
        this.additionalHttpQueryParameters = additionalQueryParameters;
        return this;
    }

    @Override
    public C build() {
        return queryDslBuilderFunction.apply(this);
    }
}
