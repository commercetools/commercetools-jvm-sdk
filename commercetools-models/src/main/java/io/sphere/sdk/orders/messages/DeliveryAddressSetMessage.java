package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.orders.commands.updateactions.SetDeliveryAddress} update action.
 */
@JsonDeserialize(as = DeliveryAddressSetMessage.class)//important to override annotation in Message class
public final class DeliveryAddressSetMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "DeliveryAddressSet";
    public static final MessageDerivateHint<DeliveryAddressSetMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, DeliveryAddressSetMessage.class, Order.referenceTypeId());

    private final String deliveryId;
    private final Address address;

    @JsonCreator
    private DeliveryAddressSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final String deliveryId, Address address) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.deliveryId = deliveryId;
        this.address = address;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public Address getAddress() {
        return address;
    }
}
