package sphere.internal;

import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;
import net.jcip.annotations.Immutable;
import sphere.CommandRequest;
import sphere.FetchRequest;
import sphere.OrderService;
import sphere.QueryRequest;

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
        return new FetchRequestAdapter<Order>(service.byId(id));
    }

    @Override public QueryRequest<Order> all() {
        return new QueryRequestAdapter<Order>(service.all());
    }

    @Override public QueryRequest<Order> byCustomerId(String customerId) {
        return new QueryRequestAdapter<Order>(service.byCustomerId(customerId));
    }

    @Override public CommandRequest<Order> updatePaymentState(String orderId, int orderVersion, PaymentState paymentState) {
        return new CommandRequestAdapter<Order>(service.updatePaymentState(orderId, orderVersion, paymentState));
    }

    @Override public CommandRequest<Order> updateShipmentState(String orderId, int orderVersion, ShipmentState shipmentState) {
        return new CommandRequestAdapter<Order>(service.updateShipmentState(orderId, orderVersion, shipmentState));
    }
}
