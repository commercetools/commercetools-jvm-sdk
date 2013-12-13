package sphere.internal;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.OrderUpdate;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;
import net.jcip.annotations.Immutable;
import play.libs.F.Promise;
import sphere.FetchRequest;
import sphere.OrderService;
import sphere.QueryRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** OrderService with Play-specific async methods. */
@Immutable
public class OrderServiceAdapter implements OrderService {
    private final io.sphere.client.shop.OrderService service;
    public OrderServiceAdapter(@Nonnull io.sphere.client.shop.OrderService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }

    @Override public FetchRequest<Order> byId(String id) {
        return Async.adapt(service.byId(id));
    }

    @Deprecated
    @Override public QueryRequest<Order> all() {
        return query();
    }

    @Override public QueryRequest<Order> query() {
        return Async.adapt(service.query());
    }

    @Deprecated
    @Override public Order updatePaymentState(VersionedId orderId, PaymentState paymentState) {
        return updateOrder(orderId, new OrderUpdate().setPaymentState(paymentState));
    }

    @Deprecated
    @Override public Promise<SphereResult<Order>> updatePaymentStateAsync(VersionedId orderId, PaymentState paymentState) {
        return updateOrderAsync(orderId, new OrderUpdate().setPaymentState(paymentState));
    }

    @Deprecated
    @Override public Order updateShipmentState(VersionedId orderId, ShipmentState shipmentState) {
        return updateOrder(orderId, new OrderUpdate().setShipmentState(shipmentState));
    }

    @Deprecated
    @Override public Promise<SphereResult<Order>> updateShipmentStateAsync(VersionedId orderId, ShipmentState shipmentState) {
        return updateOrderAsync(orderId, new OrderUpdate().setShipmentState(shipmentState));
    }

    @Override
    public Order updateOrder(VersionedId orderId, OrderUpdate orderUpdate) {
        return Async.awaitResult(updateOrderAsync(orderId, orderUpdate));
    }

    @Override
    public Promise<SphereResult<Order>> updateOrderAsync(VersionedId orderId, OrderUpdate orderUpdate) {
        return Async.execute(service.updateOrder(orderId, orderUpdate));
    }
}
