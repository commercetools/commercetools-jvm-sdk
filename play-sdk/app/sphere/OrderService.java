package sphere;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Order;
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

    /** Sets the payment state of an order. */
    public Order updatePaymentState(VersionedId orderId, PaymentState paymentState);

    /** Sets the payment state of an order asynchronously. */
    public Promise<SphereResult<Order>> updatePaymentStateAsync(VersionedId orderId, PaymentState paymentState);

    /** Sets the shipment state of an order. */
    public Order updateShipmentState(VersionedId orderId, ShipmentState shipmentState);

    /** Sets the shipment state of an order asynchronously. */
    public Promise<SphereResult<Order>> updateShipmentStateAsync(VersionedId orderId, ShipmentState shipmentState);
}
