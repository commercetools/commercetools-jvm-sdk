package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.shop.model.orders.*;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.model.QueryResult;

import java.util.Currency;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface Orders {

    /** Creates a request builder that finds an order by given id. */
    RequestBuilder<Order> byId(String id);

    /** Creates a request builder that queries all orders. */
    RequestBuilder<QueryResult<Order>> all();

    /** Sets the payment state of the order. */
    public CommandRequestBuilder<Order> updatePaymentState(String orderId, String orderVersion, PaymentState paymentState);

    /** Sets the shipment state of the order. */
    public CommandRequestBuilder<Order> updateShipmentState(String orderId, String orderVersion, ShipmentState shipmentState);


}