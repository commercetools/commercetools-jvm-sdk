package sphere.internal;

import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;
import net.jcip.annotations.Immutable;
import sphere.CommandRequest;
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

    @Override public CommandRequest<Order> updatePaymentState(String orderId, int orderVersion, PaymentState paymentState) {
        return Async.adapt(service.updatePaymentState(orderId, orderVersion, paymentState));
    }

    @Override public CommandRequest<Order> updateShipmentState(String orderId, int orderVersion, ShipmentState shipmentState) {
        return Async.adapt(service.updateShipmentState(orderId, orderVersion, shipmentState));
    }
}
