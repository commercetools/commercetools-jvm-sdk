package io.sphere.sdk.orders.messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderPaymentStateChangedMessage.class)//important to override annotation in Message class
public final class OrderPaymentStateChangedMessage extends GenericMessageImpl<Order> {

    public static final String MESSAGE_TYPE = "OrderPaymentStateChanged";
    public static final MessageDerivateHint<OrderPaymentStateChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderPaymentStateChangedMessage.class, Order.referenceTypeId());

    
    private final PaymentState paymentState;

    @JsonCreator
    private OrderPaymentStateChangedMessage(String id, Long version, ZonedDateTime createdAt, ZonedDateTime lastModifiedAt, JsonNode resource, Long sequenceNumber, Long resourceVersion, String type, PaymentState paymentState) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion,type,Order.class);
        this.paymentState = paymentState;
    }

    
    public PaymentState getPaymentState() {
        return paymentState;
    }
}
