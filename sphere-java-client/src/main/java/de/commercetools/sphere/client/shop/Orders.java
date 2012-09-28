package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.model.QueryResult;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface Orders {

    /** Creates a request builder that finds an order by given id. */
    RequestBuilder<Order> byId(String id);

    /** Creates a request builder that queries all orders. */
    RequestBuilder<QueryResult<Order>> all();

    /** Sets the payment state of the order. */
    public CommandRequestBuilder<Order> updatePaymentState(String orderId, int orderVersion, PaymentState paymentState);

    /** Sets the shipment state of the order. */
    public CommandRequestBuilder<Order> updateShipmentState(String orderId, int orderVersion, ShipmentState shipmentState);


}