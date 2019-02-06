package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ShipmentState;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderShipmentStateChangedMessage.class)//important to override annotation in Message class
public final class OrderShipmentStateChangedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "OrderShipmentStateChanged";
    public static final MessageDerivateHint<OrderShipmentStateChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderShipmentStateChangedMessage.class, Order.referenceTypeId());

    private final ShipmentState shipmentState;

    @JsonCreator
    private OrderShipmentStateChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                            final ShipmentState shipmentState) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.shipmentState = shipmentState;
    }

    public ShipmentState getShipmentState() {
        return shipmentState;
    }
}
