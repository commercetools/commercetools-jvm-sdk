package de.commercetools.internal;

import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.sphere.client.shop.Orders;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;

import org.codehaus.jackson.type.TypeReference;

public class OrdersImpl implements Orders {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public OrdersImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    /** {@inheritDoc}  */
    public RequestBuilder<Order> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.orders.byId(id), new TypeReference<Order>() {});
    }

    /** {@inheritDoc}  */
    public RequestBuilder<QueryResult<Order>> all() {
        return requestFactory.createQueryRequest(endpoints.orders.root(), new TypeReference<QueryResult<Order>>() {});
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequestBuilder<Order> createCommandRequest(String url, Command command) {
        return requestFactory.<Order>createCommandRequest(url, command, new TypeReference<Order>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Order> updatePaymentState(String orderId, int orderVersion, PaymentState paymentState) {
        return createCommandRequest(
                endpoints.orders.updatePaymentState(),
                new OrderCommands.UpdatePaymentState(orderId, orderVersion, paymentState));
    }


    /** {@inheritDoc}  */
    public CommandRequestBuilder<Order> updateShipmentState(String orderId, int orderVersion, ShipmentState shipmentState) {
        return createCommandRequest(
                endpoints.orders.updateShipmentState(),
                new OrderCommands.UpdateShipmentState(orderId, orderVersion, shipmentState));
    }

}
