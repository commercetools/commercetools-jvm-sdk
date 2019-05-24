package io.sphere.sdk.orders.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

public final class OrderInStoreQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<OrderInStoreQueryBuilder, Order, OrderInStoreQuery, OrderQueryModel, OrderExpansionModel<Order>> {

    private OrderInStoreQueryBuilder(final OrderInStoreQuery template) {
        super(template);
    }

    public static OrderInStoreQueryBuilder of(final String storeKey) {
        return new OrderInStoreQueryBuilder(OrderInStoreQuery.of(storeKey));
    }

    @Override
    protected OrderInStoreQueryBuilder getThis() {
        return this;
    }

    @Override
    public OrderInStoreQuery build() {
        return super.build();
    }

    @Override
    public OrderInStoreQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public OrderInStoreQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public OrderInStoreQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public OrderInStoreQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public OrderInStoreQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public OrderInStoreQueryBuilder plusExpansionPaths(final Function<OrderExpansionModel<Order>, ExpansionPathContainer<Order>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public OrderInStoreQueryBuilder plusPredicates(final Function<OrderQueryModel, QueryPredicate<Order>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public OrderInStoreQueryBuilder plusPredicates(final QueryPredicate<Order> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public OrderInStoreQueryBuilder plusPredicates(final List<QueryPredicate<Order>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public OrderInStoreQueryBuilder plusSort(final Function<OrderQueryModel, QuerySort<Order>> m) {
        return super.plusSort(m);
    }

    @Override
    public OrderInStoreQueryBuilder plusSort(final List<QuerySort<Order>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public OrderInStoreQueryBuilder plusSort(final QuerySort<Order> sort) {
        return super.plusSort(sort);
    }

    @Override
    public OrderInStoreQueryBuilder predicates(final Function<OrderQueryModel, QueryPredicate<Order>> m) {
        return super.predicates(m);
    }

    @Override
    public OrderInStoreQueryBuilder predicates(final QueryPredicate<Order> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public OrderInStoreQueryBuilder predicates(final List<QueryPredicate<Order>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public OrderInStoreQueryBuilder sort(final Function<OrderQueryModel, QuerySort<Order>> m) {
        return super.sort(m);
    }

    @Override
    public OrderInStoreQueryBuilder sort(final List<QuerySort<Order>> sort) {
        return super.sort(sort);
    }

    @Override
    public OrderInStoreQueryBuilder sort(final QuerySort<Order> sort) {
        return super.sort(sort);
    }

    @Override
    public OrderInStoreQueryBuilder sortMulti(final Function<OrderQueryModel, List<QuerySort<Order>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public OrderInStoreQueryBuilder expansionPaths(final Function<OrderExpansionModel<Order>, ExpansionPathContainer<Order>> m) {
        return super.expansionPaths(m);
    }
}