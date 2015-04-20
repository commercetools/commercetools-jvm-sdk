package io.sphere.sdk.orders.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

/**
 {@doc.gen summary orders}
 */
public class OrderQuery extends DefaultModelQuery<Order> {
    private OrderQuery() {
        super(OrdersEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static OrderQuery of() {
        return new OrderQuery();
    }

    public static TypeReference<PagedQueryResult<Order>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Order>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Order>>";
            }
        };
    }

    public static OrderQueryModel model() {
        return OrderQueryModel.get();
    }

    public QueryDsl<Order> byCustomerId(final String customerId) {
        return withPredicate(model().customerId().is(customerId));
    }

    public QueryDsl<Order> byCustomerEmail(final String customerEmail) {
        return withPredicate(model().customerEmail().is(customerEmail));
    }
}
