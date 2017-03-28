package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderStateChangedMessage.class)//important to override annotation in Message class
@JsonTypeName(OrderStateChangedMessage.MESSAGE_TYPE)
public final class OrderStateChangedMessage extends GenericMessageImpl<Order> {
    public static final String MESSAGE_TYPE = "OrderStateChanged";
    public static final MessageDerivateHint<OrderStateChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderStateChangedMessage.class, Order.referenceTypeId());

    private final OrderState orderState;

    @JsonCreator
    private OrderStateChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final OrderState orderState) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Order.class);
        this.orderState = orderState;
    }

    public OrderState getOrderState() {
        return orderState;
    }
}
