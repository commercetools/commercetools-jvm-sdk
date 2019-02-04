package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Delivery;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.Parcel;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.orders.commands.updateactions.AddParcelToDelivery} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#addParcelToDelivery()}
 *
 */
@JsonDeserialize(as = ParcelAddedToDeliveryMessage.class)//important to override annotation in Message class
public final class ParcelAddedToDeliveryMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "ParcelAddedToDelivery";
    public static final MessageDerivateHint<ParcelAddedToDeliveryMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ParcelAddedToDeliveryMessage.class, Order.referenceTypeId());

    private final Delivery delivery;
    private final Parcel parcel;

    @JsonCreator
    private ParcelAddedToDeliveryMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Class<Order> clazz, final Delivery delivery, final Parcel parcel) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.delivery = delivery;
        this.parcel = parcel;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Parcel getParcel() {
        return parcel;
    }
}
