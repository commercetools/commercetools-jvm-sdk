package io.sphere.sdk.carts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.OrderLike;

import java.util.Optional;

@JsonDeserialize(as=CartImpl.class)
public interface Cart extends OrderLike<Cart> {

    public static String typeId(){
        return "cart";
    }

    public static TypeReference<Cart> typeReference() {
        return new TypeReference<Cart>() {
            @Override
            public String toString() {
                return "TypeReference<Cart>";
            }
        };
    }

    @Override
    default Reference<Cart> toReference() {
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    CartState getCartState();

    InventoryMode getInventoryMode();

    Optional<CartShippingInfo> getShippingInfo();
}
