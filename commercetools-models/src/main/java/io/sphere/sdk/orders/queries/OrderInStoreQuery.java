package io.sphere.sdk.orders.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;

import java.util.List;
import java.util.function.Function;

public interface OrderInStoreQuery extends MetaModelQueryDsl<Order, OrderInStoreQuery, OrderQueryModel, OrderExpansionModel<Order>> {

    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<PagedQueryResult<Order>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Order>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Order>>";
            }
        };
    }

    static OrderInStoreQuery of(final String storeKey) {
        return new OrderInStoreQueryImpl(storeKey);
    }

    @Override
    OrderInStoreQuery plusPredicates(final Function<OrderQueryModel, QueryPredicate<Order>> m);

    @Override
    OrderInStoreQuery plusPredicates(final QueryPredicate<Order> queryPredicate);

    @Override
    OrderInStoreQuery plusPredicates(final List<QueryPredicate<Order>> queryPredicates);

    @Override
    OrderInStoreQuery plusSort(final Function<OrderQueryModel, QuerySort<Order>> m);

    @Override
    OrderInStoreQuery plusSort(final List<QuerySort<Order>> sort);

    @Override
    OrderInStoreQuery plusSort(final QuerySort<Order> sort);

    @Override
    OrderInStoreQuery withPredicates(final Function<OrderQueryModel, QueryPredicate<Order>> predicateFunction);

    @Override
    OrderInStoreQuery withPredicates(final QueryPredicate<Order> queryPredicate);

    @Override
    OrderInStoreQuery withPredicates(final List<QueryPredicate<Order>> queryPredicates);

    @Override
    OrderInStoreQuery withSort(final Function<OrderQueryModel, QuerySort<Order>> m);

    @Override
    OrderInStoreQuery withSort(final List<QuerySort<Order>> sort);

    @Override
    OrderInStoreQuery withSort(final QuerySort<Order> sort);

    @Override
    OrderInStoreQuery withSortMulti(final Function<OrderQueryModel, List<QuerySort<Order>>> m);

    @Override
    OrderInStoreQuery plusExpansionPaths(final Function<OrderExpansionModel<Order>, ExpansionPathContainer<Order>> m);

    @Override
    OrderInStoreQuery withExpansionPaths(final Function<OrderExpansionModel<Order>, ExpansionPathContainer<Order>> m);

    @Override
    OrderInStoreQuery withFetchTotal(final boolean fetchTotal);

    @Override
    OrderInStoreQuery withLimit(final Long limit);

    @Override
    OrderInStoreQuery withOffset(final Long offset);
    
    default OrderInStoreQuery byCustomerId(final String customerId) {
        return withPredicates(m -> m.customerId().is(customerId));
    }

    default OrderInStoreQuery byCustomerEmail(final String customerEmail) {
        return withPredicates(m -> m.customerEmail().is(customerEmail));
    }
}