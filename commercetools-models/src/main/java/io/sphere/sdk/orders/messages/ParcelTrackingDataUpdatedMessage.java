package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.TrackingData;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.orders.commands.updateactions.SetParcelTrackingData} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setParcelTrackingData()}
 *
 */
@JsonDeserialize(as = ParcelTrackingDataUpdatedMessage.class)//important to override annotation in Message class
public final class ParcelTrackingDataUpdatedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "ParcelTrackingDataUpdated";
    public static final MessageDerivateHint<ParcelTrackingDataUpdatedMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ParcelTrackingDataUpdatedMessage.class, Order.referenceTypeId());

    private final String deliveryId;
    private final String parcelId;
    private final TrackingData trackingData;

    @JsonCreator
    private ParcelTrackingDataUpdatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                            final String deliveryId, final String parcelId, final TrackingData trackingData) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.deliveryId = deliveryId;
        this.parcelId = parcelId;
        this.trackingData = trackingData;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public String getParcelId() {
        return parcelId;
    }

    public TrackingData getTrackingData() {
        return trackingData;
    }
}
