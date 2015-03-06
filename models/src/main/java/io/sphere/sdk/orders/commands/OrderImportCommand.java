package io.sphere.sdk.orders.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.orders.ImportOrder;
import io.sphere.sdk.orders.Order;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 * Orders can also be imported via the Import API endpoint so you don't have to create a cart before.
 */
public class OrderImportCommand extends CommandImpl<Order> {
    private final ImportOrder importOrder;

    private OrderImportCommand(final ImportOrder importOrder) {
        this.importOrder = importOrder;
    }

    @Override
    protected TypeReference<Order> typeReference() {
        return Order.typeReference();
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, OrdersEndpoint.ENDPOINT.endpoint() + "/import", JsonUtils.toJson(importOrder));
    }

    public static OrderImportCommand of(final ImportOrder importOrder) {
        return new OrderImportCommand(importOrder);
    }
}
