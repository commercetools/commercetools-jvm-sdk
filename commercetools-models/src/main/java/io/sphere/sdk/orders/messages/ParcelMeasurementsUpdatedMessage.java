package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ParcelMeasurements;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.orders.commands.updateactions.SetParcelMeasurements} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setParcelMeasurements()}
 *
 */
@JsonDeserialize(as = ParcelMeasurementsUpdatedMessage.class)//important to override annotation in Message class
public final class ParcelMeasurementsUpdatedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "ParcelMeasurementsUpdated";
    public static final MessageDerivateHint<ParcelMeasurementsUpdatedMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ParcelMeasurementsUpdatedMessage.class, Order.referenceTypeId());

    private final String deliveryId;
    private final String parcelId;
    private final ParcelMeasurements measurements;

    ParcelMeasurementsUpdatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                            final String deliveryId, final String parcelId, final ParcelMeasurements measurements) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.deliveryId = deliveryId;
        this.parcelId = parcelId;
        this.measurements = measurements;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public String getParcelId() {
        return parcelId;
    }

    public ParcelMeasurements getMeasurements() {
        return measurements;
    }
}
