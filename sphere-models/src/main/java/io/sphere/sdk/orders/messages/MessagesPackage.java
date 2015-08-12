package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;

final class MessagesPackage {
    public static final TypeReference<Reference<Order>> ORDER_REFERENCE_TYPE_REFERENCE = new TypeReference<Reference<Order>>() {
        @Override
        public String toString() {
            return "TypeReference<Reference<Order>>";
        }
    };

    private MessagesPackage() {
    }
}
