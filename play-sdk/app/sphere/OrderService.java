package sphere;

import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;
import play.libs.F.Promise;

/** Sphere HTTP API for working with orders in a given project. */
public interface OrderService {
    /** Finds an order by an id. */
    FetchRequest<Order> byId(String id);

    /** Queries all orders in current project. */
    QueryRequest<Order> all();

    /** Sets the payment state of an order. */
    public Order updatePaymentState(String orderId, int orderVersion, PaymentState paymentState);

    /** Sets the payment state of an order asynchronously. */
    public Promise<Order> updatePaymentStateAsync(String orderId, int orderVersion, PaymentState paymentState);

    /** Sets the shipment state of an order. */
    public Order updateShipmentState(String orderId, int orderVersion, ShipmentState shipmentState);

    /** Sets the shipment state of an order asynchronously. */
    public Promise<Order> updateShipmentStateAsync(String orderId, int orderVersion, ShipmentState shipmentState);
}
