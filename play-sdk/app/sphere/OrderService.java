package sphere;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.OrderUpdate;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;
import play.libs.F.Promise;

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

    /**
     * Sets the payment state of an order.
     *
     * @deprecated since 0.51.0, use {@link #updateOrder(io.sphere.client.model.VersionedId, io.sphere.client.shop.model.OrderUpdate)}
     * instead, e.g.
     * {@code updateOrder(orderId, new OrderUpdate().setPaymentState(paymentState));}.
     **/
    @Deprecated
    public Order updatePaymentState(VersionedId orderId, PaymentState paymentState);

    /**
     * Sets the payment state of an order.
     *
     * @deprecated since 0.51.0, use {@link #updateOrderAsync(io.sphere.client.model.VersionedId, io.sphere.client.shop.model.OrderUpdate)}
     * instead, e.g.
     * {@code updateOrder(orderId, new OrderUpdate().setPaymentState(paymentState));}.
     **/
    @Deprecated
    public Promise<SphereResult<Order>> updatePaymentStateAsync(VersionedId orderId, PaymentState paymentState);

    /** Sets the shipment state of an order.
     *
     * @deprecated since 0.51.0, use {@link #updateOrder(io.sphere.client.model.VersionedId, io.sphere.client.shop.model.OrderUpdate)}
     * instead, e.g.
     * {@code updateOrder(orderId, new OrderUpdate().setShipmentState(shipmentState));}.
     **/
    @Deprecated
    public Order updateShipmentState(VersionedId orderId, ShipmentState shipmentState);

    /** Sets the shipment state of an order.
     *
     * @deprecated since 0.51.0, use {@link #updateOrderAsync(io.sphere.client.model.VersionedId, io.sphere.client.shop.model.OrderUpdate)}
     * instead, e.g.
     * {@code updateOrder(orderId, new OrderUpdate().setShipmentState(shipmentState));}.
     **/
    @Deprecated
    public Promise<SphereResult<Order>> updateShipmentStateAsync(VersionedId orderId, ShipmentState shipmentState);

    /**
     * Updates the order, e.g. the payment state, the shipment state or the parcel tracking data.
     * @param orderId the versioned id of the order to update
     * @param orderUpdate the changes to be applied
     */
    public Order updateOrder(VersionedId orderId, OrderUpdate orderUpdate);

    /**
     * Updates the order, e.g. the payment state, the shipment state or the parcel tracking data asynchronously.
     * @param orderId the versioned id of the order to update
     * @param orderUpdate the changes to be applied
     */
    public Promise<SphereResult<Order>> updateOrderAsync(VersionedId orderId, OrderUpdate orderUpdate);
}
