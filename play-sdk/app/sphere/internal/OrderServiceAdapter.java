package sphere.internal;

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

    @Override public Order updatePaymentState(String orderId, int orderVersion, PaymentState paymentState) {
        return Async.await(updatePaymentStateAsync(orderId, orderVersion, paymentState));
    }

    @Override public Promise<Order> updatePaymentStateAsync(String orderId, int orderVersion, PaymentState paymentState) {
        return Async.execute(service.updatePaymentState(orderId, orderVersion, paymentState));
    }

    @Override public Order updateShipmentState(String orderId, int orderVersion, ShipmentState shipmentState) {
        return Async.await(updateShipmentStateAsync(orderId, orderVersion, shipmentState));
    }

    @Override public Promise<Order> updateShipmentStateAsync(String orderId, int orderVersion, ShipmentState shipmentState) {
        return Async.execute(service.updateShipmentState(orderId, orderVersion, shipmentState));
    }
}
