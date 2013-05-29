package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.VersionedId;
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
    public CommandRequest<Order> updatePaymentState(VersionedId orderId, PaymentState paymentState);

    /** Sets the shipment state of an order. */
    public CommandRequest<Order> updateShipmentState(VersionedId orderId, ShipmentState shipmentState);

    /** Creates an order based on a cart, and deletes the cart.
     *  The created order object has the same id as the cart it was created from.
     *
     *  @return A command request which can fail with the following exceptions:
     *  <ul>
     *      <li>{@link OutOfStockException} if some of the products in the cart are not available anymore.
     *          This can only happen if the cart is in the
     *          {@link io.sphere.client.shop.model.Cart.InventoryMode#ReserveOnOrder ReserveOnOrder} mode.
     *      <li>{@link PriceChangedException} if the price, tax or shipping of some line items changed since the items
     *          were added to the cart.
     *  </ul>*/
    CommandRequest<Order> createOrder(VersionedId cartId);

    /** Creates an order based on a cart, and deletes the cart.
     *  The created order object has the same id as the cart it was created from.
     *
     *  @return A command request which can fail with the following exceptions:
     *  <ul>
     *      <li>{@link OutOfStockException} if some of the products in the cart are not available anymore.
     *          This can only happen if the cart is in the
     *          {@link io.sphere.client.shop.model.Cart.InventoryMode#ReserveOnOrder ReserveOnOrder} mode.
     *      <li>{@link PriceChangedException} if the price, tax or shipping of some line items changed since the items
     *          were added to the cart.
     *  </ul>*/
    CommandRequest<Order> createOrder(VersionedId cartId, PaymentState paymentState);
}
