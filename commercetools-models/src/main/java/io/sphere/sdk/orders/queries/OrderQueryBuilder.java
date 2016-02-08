package io.sphere.sdk.orders.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class OrderQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<OrderQueryBuilder, Order, OrderQuery, OrderQueryModel, OrderExpansionModel<Order>> {

    private OrderQueryBuilder(final OrderQuery template) {
        super(template);
    }

    public static OrderQueryBuilder of() {
        return new OrderQueryBuilder(OrderQuery.of());
    }

    @Override
    protected OrderQueryBuilder getThis() {
        return this;
    }

    @Override
    public OrderQuery build() {
        return super.build();
    }

    @Override
    public OrderQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public OrderQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public OrderQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public OrderQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public OrderQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public OrderQueryBuilder plusExpansionPaths(final Function<OrderExpansionModel<Order>, ExpansionPathContainer<Order>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public OrderQueryBuilder plusPredicates(final Function<OrderQueryModel, QueryPredicate<Order>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public OrderQueryBuilder plusPredicates(final QueryPredicate<Order> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public OrderQueryBuilder plusPredicates(final List<QueryPredicate<Order>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public OrderQueryBuilder plusSort(final Function<OrderQueryModel, QuerySort<Order>> m) {
        return super.plusSort(m);
    }

    @Override
    public OrderQueryBuilder plusSort(final List<QuerySort<Order>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public OrderQueryBuilder plusSort(final QuerySort<Order> sort) {
        return super.plusSort(sort);
    }

    @Override
    public OrderQueryBuilder predicates(final Function<OrderQueryModel, QueryPredicate<Order>> m) {
        return super.predicates(m);
    }

    @Override
    public OrderQueryBuilder predicates(final QueryPredicate<Order> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public OrderQueryBuilder predicates(final List<QueryPredicate<Order>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public OrderQueryBuilder sort(final Function<OrderQueryModel, QuerySort<Order>> m) {
        return super.sort(m);
    }

    @Override
    public OrderQueryBuilder sort(final List<QuerySort<Order>> sort) {
        return super.sort(sort);
    }

    @Override
    public OrderQueryBuilder sort(final QuerySort<Order> sort) {
        return super.sort(sort);
    }

    @Override
    public OrderQueryBuilder sortMulti(final Function<OrderQueryModel, List<QuerySort<Order>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public OrderQueryBuilder expansionPaths(final Function<OrderExpansionModel<Order>, ExpansionPathContainer<Order>> m) {
        return super.expansionPaths(m);
    }
}
