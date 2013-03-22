package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.*;
import io.sphere.client.model.QueryResult;

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
}
