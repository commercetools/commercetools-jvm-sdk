package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.DeliveryItem;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * This message is the result of the {@link io.sphere.sdk.orders.commands.updateactions.SetParcelItems} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setParcelItems()}
 *
 */
@JsonDeserialize(as = ParcelItemsUpdatedMessage.class)//important to override annotation in Message class
public final class ParcelItemsUpdatedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "ParcelItemsUpdated";
    public static final MessageDerivateHint<ParcelItemsUpdatedMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ParcelItemsUpdatedMessage.class, Order.referenceTypeId());
    private final String deliveryId;
    private final String parcelId;
    private final List<DeliveryItem> items;

    ParcelItemsUpdatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                     final String parcelId,final String deliveryId, final List<DeliveryItem> items) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.parcelId = parcelId;
        this.items = items;
        this.deliveryId = deliveryId;
    }

    public String getParcelId() {
        return parcelId;
    }

    public List<DeliveryItem> getItems() {
        return items;
    }

    public String getDeliveryId() {
        return deliveryId;
    }
}
