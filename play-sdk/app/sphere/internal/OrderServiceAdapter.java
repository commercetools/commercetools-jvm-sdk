package sphere.internal;

import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Order;
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

    @Override public QueryRequest<Order> all() {
        return Async.adapt(service.all());
    }

    @Override public Order updatePaymentState(VersionedId orderId, PaymentState paymentState) {
        return Async.await(updatePaymentStateAsync(orderId, paymentState));
    }

    @Override public Promise<Order> updatePaymentStateAsync(VersionedId orderId, PaymentState paymentState) {
        return Async.execute(service.updatePaymentState(orderId, paymentState));
    }

    @Override public Order updateShipmentState(VersionedId orderId, ShipmentState shipmentState) {
        return Async.await(updateShipmentStateAsync(orderId, shipmentState));
    }

    @Override public Promise<Order> updateShipmentStateAsync(VersionedId orderId, ShipmentState shipmentState) {
        return Async.execute(service.updateShipmentState(orderId, shipmentState));
    }
}
