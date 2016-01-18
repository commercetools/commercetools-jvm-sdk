package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

public class ChannelQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ChannelQueryBuilder, Channel, ChannelQuery, ChannelQueryModel, ChannelExpansionModel<Channel>> {

    private ChannelQueryBuilder(final ChannelQuery template) {
        super(template);
    }

    public static ChannelQueryBuilder of() {
        return new ChannelQueryBuilder(ChannelQuery.of());
    }

    @Override
    protected ChannelQueryBuilder getThis() {
        return this;
    }

    @Override
    public ChannelQuery build() {
        return super.build();
    }

    @Override
    public ChannelQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ChannelQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ChannelQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ChannelQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ChannelQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ChannelQueryBuilder plusExpansionPaths(final Function<ChannelExpansionModel<Channel>, ExpansionPathContainer<Channel>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ChannelQueryBuilder plusPredicates(final Function<ChannelQueryModel, QueryPredicate<Channel>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ChannelQueryBuilder plusPredicates(final QueryPredicate<Channel> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ChannelQueryBuilder plusPredicates(final List<QueryPredicate<Channel>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ChannelQueryBuilder plusSort(final Function<ChannelQueryModel, QuerySort<Channel>> m) {
        return super.plusSort(m);
    }

    @Override
    public ChannelQueryBuilder plusSort(final List<QuerySort<Channel>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ChannelQueryBuilder plusSort(final QuerySort<Channel> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ChannelQueryBuilder predicates(final Function<ChannelQueryModel, QueryPredicate<Channel>> m) {
        return super.predicates(m);
    }

    @Override
    public ChannelQueryBuilder predicates(final QueryPredicate<Channel> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ChannelQueryBuilder predicates(final List<QueryPredicate<Channel>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ChannelQueryBuilder sort(final Function<ChannelQueryModel, QuerySort<Channel>> m) {
        return super.sort(m);
    }

    @Override
    public ChannelQueryBuilder sort(final List<QuerySort<Channel>> sort) {
        return super.sort(sort);
    }

    @Override
    public ChannelQueryBuilder sort(final QuerySort<Channel> sort) {
        return super.sort(sort);
    }

    @Override
    public ChannelQueryBuilder sortMulti(final Function<ChannelQueryModel, List<QuerySort<Channel>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ChannelQueryBuilder expansionPaths(final Function<ChannelExpansionModel<Channel>, ExpansionPathContainer<Channel>> m) {
        return super.expansionPaths(m);
    }
}
