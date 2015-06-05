package io.sphere.sdk.orders.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.UltraQueryDsl;

/**
 {@doc.gen summary orders}
 */
public interface OrderQuery extends UltraQueryDsl<Order, OrderQuery, OrderQueryModel<Order>, OrderExpansionModel<Order>> {
    static OrderQuery of() {
        return new OrderQueryImpl();
    }

    static TypeReference<PagedQueryResult<Order>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Order>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Order>>";
            }
        };
    }

    default OrderQuery byCustomerId(final String customerId) {
        return withPredicate(OrderQueryModel.of().customerId().is(customerId));
    }

    default OrderQuery byCustomerEmail(final String customerEmail) {
        return withPredicate(OrderQueryModel.of().customerEmail().is(customerEmail));
    }
}
