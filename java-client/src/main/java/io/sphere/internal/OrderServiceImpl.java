package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.OrderService;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;
import io.sphere.internal.command.CartCommands;
import io.sphere.internal.command.Command;
import io.sphere.internal.command.OrderCommands;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

public class OrderServiceImpl implements OrderService {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public OrderServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    @Override public FetchRequest<Order> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.orders.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Order>() {});
    }

    @Override public QueryRequest<Order> all() {
        return requestFactory.createQueryRequest(
                endpoints.orders.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Order>>() {});
    }

    @Override public QueryRequest<Order> byCustomerId(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.orders.queryByCustomerId(customerId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Order>>() {});
    }

    @Override public CommandRequest<Order> updatePaymentState(VersionedId orderId, PaymentState paymentState) {
        return createCommandRequest(
                endpoints.orders.updatePaymentState(),
                new OrderCommands.UpdatePaymentState(orderId.getId(), orderId.getVersion(), paymentState));
    }


    @Override public CommandRequest<Order> updateShipmentState(VersionedId orderId, ShipmentState shipmentState) {
        return createCommandRequest(
                endpoints.orders.updateShipmentState(),
                new OrderCommands.UpdateShipmentState(orderId.getId(), orderId.getVersion(), shipmentState));
    }


    @Override public CommandRequest<Order> createOrder(VersionedId cartId, PaymentState paymentState) {
        return requestFactory.createCommandRequest(
                endpoints.orders.root(),
                new CartCommands.OrderCart(cartId.getId(), cartId.getVersion(), paymentState),
                new TypeReference<Order>() {});
    }

    @Override public CommandRequest<Order> createOrder(VersionedId cartId) {
        return createOrder(cartId, null);
    }

    /** Helper to save some repetitive code. */
    private CommandRequest<Order> createCommandRequest(String url, Command command) {
        return requestFactory.<Order>createCommandRequest(url, command, new TypeReference<Order>() {});
    }
}
