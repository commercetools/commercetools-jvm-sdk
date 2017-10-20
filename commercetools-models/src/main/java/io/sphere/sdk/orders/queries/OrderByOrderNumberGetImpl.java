package io.sphere.sdk.orders.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.http.UriTemplate;
import io.sphere.sdk.http.UrlBuilder;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class OrderByOrderNumberGetImpl extends MetaModelGetDslImpl<Order, Order, OrderByOrderNumberGet, OrderExpansionModel<Order>> implements OrderByOrderNumberGet {
    static final UriTemplate GET_BY_ORDER_NUMBER = UriTemplate.of("/orders/order-number={orderNumber}");

    OrderByOrderNumberGetImpl(final String orderNumber) {
        super("", buildEndpoint(orderNumber), OrderExpansionModel.of(), OrderByOrderNumberGetImpl::new);
    }

    private static JsonEndpoint<Order> buildEndpoint(final String orderNumber) {
        final String endpoint = UrlBuilder.of(GET_BY_ORDER_NUMBER)
                .addUriParameter(orderNumber)
                .build();
        return JsonEndpoint.of(Order.typeReference(), endpoint);
    }

    OrderByOrderNumberGetImpl(final MetaModelGetDslBuilder<Order, Order, OrderByOrderNumberGet, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}
