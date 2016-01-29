package io.sphere.sdk.orders.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**
 {@doc.gen summary orders}
 */
public interface OrderQuery extends MetaModelQueryDsl<Order, OrderQuery, OrderQueryModel, OrderExpansionModel<Order>> {
    static OrderQuery of() {
        return new OrderQueryImpl();
    }

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
        return new TypeReference<PagedQueryResult<Order>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Order>>";
            }
        };
    }

    default OrderQuery byCustomerId(final String customerId) {
        return withPredicates(m -> m.customerId().is(customerId));
    }

    default OrderQuery byCustomerEmail(final String customerEmail) {
        return withPredicates(m -> m.customerEmail().is(customerEmail));
    }
}
