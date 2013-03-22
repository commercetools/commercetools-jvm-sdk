package io.sphere.internal;

import io.sphere.internal.command.Command;
import io.sphere.internal.command.OrderCommands;
import io.sphere.internal.request.RequestFactory;
import io.sphere.client.FetchRequest;
import io.sphere.client.shop.OrderService;
import io.sphere.client.shop.model.*;
import io.sphere.client.model.QueryResult;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.CommandRequest;

import org.codehaus.jackson.type.TypeReference;

public class OrderServiceImpl implements OrderService {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public OrderServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    /** {@inheritDoc}  */
    public FetchRequest<Order> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.orders.byId(id), new TypeReference<Order>() {
        });
    }

    /** {@inheritDoc}  */
    public QueryRequest<Order> all() {
        return requestFactory.createQueryRequest(endpoints.orders.root(), new TypeReference<QueryResult<Order>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Order> byCustomerId(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.orders.queryByCustomerId(customerId),
                new TypeReference<QueryResult<Order>>() {});
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Order> createCommandRequest(String url, Command command) {
        return requestFactory.<Order>createCommandRequest(url, command, new TypeReference<Order>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Order> updatePaymentState(String orderId, int orderVersion, PaymentState paymentState) {
        return createCommandRequest(
                endpoints.orders.updatePaymentState(),
                new OrderCommands.UpdatePaymentState(orderId, orderVersion, paymentState));
    }


    /** {@inheritDoc}  */
    public CommandRequest<Order> updateShipmentState(String orderId, int orderVersion, ShipmentState shipmentState) {
        return createCommandRequest(
                endpoints.orders.updateShipmentState(),
                new OrderCommands.UpdateShipmentState(orderId, orderVersion, shipmentState));
    }

}
