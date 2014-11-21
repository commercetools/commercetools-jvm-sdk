package io.sphere.sdk.orders;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.carts.InventoryMode;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

public interface Order extends OrderLike<Order> {
    public static String typeId(){
        return "order";
    }

    public static TypeReference<Order> typeReference() {
        return new TypeReference<Order>() {
            @Override
            public String toString() {
                return "TypeReference<Order>";
            }
        };
    }

    @Override
    default Reference<Order> toReference() {
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    Optional<String> getOrderNumber();

    InventoryMode getInventoryMode();

    OrderState getOrderState();

    Optional<ShipmentState> getShipmentState();

    Optional<PaymentState> paymentState();

    Optional<OrderShippingInfo> getShippingInfo();

    Set<SyncInfo> getSyncInfo();

    Set<ReturnInfo> getReturnInfo();

    long getLastMessageSequenceNumber();
}
