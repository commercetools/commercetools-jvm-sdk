package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.Parcel;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.orders.commands.updateactions.RemoveParcelFromDelivery} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#removeParcelFromDelivery()}
 *
 */
@JsonDeserialize(as = ParcelRemovedFromDeliveryMessage.class)//important to override annotation in Message class
public final class ParcelRemovedFromDeliveryMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "ParcelRemovedFromDelivery";
    public static final MessageDerivateHint<ParcelRemovedFromDeliveryMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ParcelRemovedFromDeliveryMessage.class, Order.referenceTypeId());

    private final String deliveryId;
    private final Parcel parcel;

    @JsonCreator
    private ParcelRemovedFromDeliveryMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource,
                                            final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                            final String deliveryId, final Parcel parcel) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.deliveryId = deliveryId;
        this.parcel = parcel;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public Parcel getParcel() {
        return parcel;
    }
}
