package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.OrderUpdate;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;

/** Sphere HTTP API for working with orders in a given project. */
public interface OrderService {
    /** Finds an order by an id. */
    FetchRequest<Order> byId(String id);

    /** Queries all orders in current project.
     *
     * @deprecated since 0.49.0. Use {@link #query()} instead.
     **/
    @Deprecated
    QueryRequest<Order> all();

    /** Queries orders in current project. */
    QueryRequest<Order> query();

    /** Queries all orders of given customer. */
    public QueryRequest<Order> forCustomer(String customerId);

    /**
     * Sets the payment state of an order.
     *
     * @deprecated since 0.51.0, use {@link #updateOrder(io.sphere.client.model.VersionedId, io.sphere.client.shop.model.OrderUpdate)}
     * instead, e.g.
     * {@code updateOrder(orderId, new OrderUpdate().setPaymentState(paymentState));}.
     **/
    @Deprecated
    public CommandRequest<Order> updatePaymentState(VersionedId orderId, PaymentState paymentState);

    /** Sets the shipment state of an order.
     *
     * @deprecated since 0.51.0, use {@link #updateOrder(io.sphere.client.model.VersionedId, io.sphere.client.shop.model.OrderUpdate)}
     * instead, e.g.
     * {@code updateOrder(orderId, new OrderUpdate().setShipmentState(shipmentState));}.
     **/
    @Deprecated
    public CommandRequest<Order> updateShipmentState(VersionedId orderId, ShipmentState shipmentState);

    /**
     * Updates the order, e.g. the payment state, the shipment state or the parcel tracking data.
     * @param orderId the versioned id of the order to update
     * @param orderUpdate the changes to be applied
     * @return a command that needs to be executed to perform the changes
     */
    public CommandRequest<Order> updateOrder(VersionedId orderId, OrderUpdate orderUpdate);

    /** Creates an order based on a cart, and deletes the cart.
     *  The created order object has the same id as the cart it was created from.
     *
     *  @return A command request which can fail with the following exceptions:
     *  <ul>
     *      <li>{@link io.sphere.client.exceptions.OutOfStockException OutOfStockException} if some of the products
     *          in the cart are not available anymore.
     *          This can only happen if the cart is in the
     *          {@link io.sphere.client.shop.model.Cart.InventoryMode#ReserveOnOrder ReserveOnOrder} mode.
     *      <li>{@link io.sphere.client.exceptions.PriceChangedException PriceChangedException} if the price, tax or
     *          shipping of some line items changed since the items were added to the cart.
     *  </ul>*/
    CommandRequest<Order> createOrder(VersionedId cartId);

    /** Creates an order based on a cart, and deletes the cart.
     *  The created order object has the same id as the cart it was created from.
     *
     *  @return A command request which can fail with the following exceptions:
     *  <ul>
     *      <li>{@link io.sphere.client.exceptions.OutOfStockException OutOfStockException} if some of the products
     *          in the cart are not available anymore.
     *          This can only happen if the cart is in the
     *          {@link io.sphere.client.shop.model.Cart.InventoryMode#ReserveOnOrder ReserveOnOrder} mode.
     *      <li>{@link io.sphere.client.exceptions.PriceChangedException PriceChangedException} if the price, tax or
     *          shipping of some line items changed since the items were added to the cart.
     *  </ul>*/
    CommandRequest<Order> createOrder(VersionedId cartId, PaymentState paymentState);
}
