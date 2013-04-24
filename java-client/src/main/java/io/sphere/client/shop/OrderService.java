package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;

/** Sphere HTTP API for working with orders in a given project. */
public interface OrderService {
    /** Finds an order by an id. */
    FetchRequest<Order> byId(String id);

    /** Queries all orders in current project. */
    QueryRequest<Order> all();

    /** Queries all orders of given customer. */
    public QueryRequest<Order> byCustomerId(String customerId);

    /** Sets the payment state of an order. */
    public CommandRequest<Order> updatePaymentState(String orderId, int orderVersion, PaymentState paymentState);

    /** Sets the shipment state of an order. */
    public CommandRequest<Order> updateShipmentState(String orderId, int orderVersion, ShipmentState shipmentState);

    /** Creates an order based on a cart, and deletes the cart.
     * The created order object has the same id as the cart it was created from. */
    CommandRequest<Order> orderCart(String cartId, int cartVersion);

    /** Creates an order based on a cart, and deletes the cart.
     * The created order object has the same id as the cart it was created from. */
    CommandRequest<Order> orderCart(String cartId, int cartVersion, PaymentState paymentState);
}
