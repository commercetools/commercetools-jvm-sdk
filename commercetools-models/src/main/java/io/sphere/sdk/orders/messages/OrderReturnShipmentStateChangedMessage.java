package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ReturnShipmentState;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderReturnShipmentStateChangedMessage.class)//important to override annotation in Message class
public final class OrderReturnShipmentStateChangedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "OrderReturnShipmentStateChanged";
    public static final MessageDerivateHint<OrderReturnShipmentStateChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderReturnShipmentStateChangedMessage.class, Order.referenceTypeId());

    private final String returnItemId;
    private final ReturnShipmentState returnShipmentState;

    @JsonCreator
    private OrderReturnShipmentStateChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                                  final String returnItemId, final ReturnShipmentState returnShipmentState) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.returnItemId = returnItemId;
        this.returnShipmentState = returnShipmentState;
    }

    public String getReturnItemId() {
        return returnItemId;
    }

    public ReturnShipmentState getReturnShipmentState() {
        return returnShipmentState;
    }
}
