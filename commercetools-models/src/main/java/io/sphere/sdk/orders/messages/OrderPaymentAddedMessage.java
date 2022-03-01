package io.sphere.sdk.orders.messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.payments.Payment;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderPaymentAddedMessage.class)//important to override annotation in Message class
public final class OrderPaymentAddedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "OrderPaymentAdded";
    public static final MessageDerivateHint<OrderPaymentAddedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderPaymentAddedMessage.class, Order.referenceTypeId());


    private final Payment payment;

    @JsonCreator
    private OrderPaymentAddedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Payment payment) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, Order.class);
        this.payment = payment;
    }

    
    public Payment getPayment() {
        return payment;
    }
}
