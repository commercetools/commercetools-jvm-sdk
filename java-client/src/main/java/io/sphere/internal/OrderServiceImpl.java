package io.sphere.internal;

import com.google.common.base.Function;
import io.sphere.client.*;
import io.sphere.client.exceptions.OutOfStockException;
import io.sphere.client.exceptions.PriceChangedException;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.SphereException;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.OrderService;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.ShipmentState;
import io.sphere.internal.command.CartCommands;
import io.sphere.internal.command.OrderCommands;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

import static io.sphere.internal.util.Util.*;

import javax.annotation.Nullable;

public class OrderServiceImpl extends ProjectScopedAPI<Order> implements OrderService {
    public OrderServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<Order>() {});
    }

    @Override public FetchRequest<Order> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.orders.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Order>() {});
    }

    @Deprecated
    @Override public QueryRequest<Order> all() {
        return query();
    }

    @Override public QueryRequest<Order> query() {
        return requestFactory.createQueryRequest(
                endpoints.orders.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Order>>() {});
    }

    @Override public QueryRequest<Order> forCustomer(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.orders.queryByCustomerId(customerId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Order>>() {});
    }

    @Override public CommandRequest<Order> updatePaymentState(VersionedId orderId, PaymentState paymentState) {
        return createCommandRequest(
                endpoints.orders.updatePaymentState(),
                new OrderCommands.UpdatePaymentState(orderId.getId(), orderId.getVersion(), paymentState));
    }


    @Override public CommandRequest<Order> updateShipmentState(VersionedId orderId, ShipmentState shipmentState) {
        return createCommandRequest(
                endpoints.orders.updateShipmentState(),
                new OrderCommands.UpdateShipmentState(orderId.getId(), orderId.getVersion(), shipmentState));
    }


    @Override public CommandRequest<Order> createOrder(VersionedId cartId, PaymentState paymentState) {
        return requestFactory.createCommandRequest(
                endpoints.orders.root(),
                new CartCommands.OrderCart(cartId.getId(), cartId.getVersion(), paymentState),
                new TypeReference<Order>() {}).
                withErrorHandling(new Function<SphereBackendException, SphereException>() {
                    public SphereException apply(@Nullable SphereBackendException e) {
                        SphereError.OutOfStock outOfStockError = getError(e, SphereError.OutOfStock.class);
                        if (outOfStockError != null)
                            return new OutOfStockException(outOfStockError.getLineItemIds());
                        SphereError.PriceChanged priceChangedError = getError(e, SphereError.PriceChanged.class);
                        if (priceChangedError != null)
                            return new PriceChangedException(priceChangedError.getLineItemIds());
                        return null;
                    }
                });
    }

    @Override public CommandRequest<Order> createOrder(VersionedId cartId) {
        return createOrder(cartId, null);
    }
}
