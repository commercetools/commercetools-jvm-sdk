package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.orders.Delivery;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.PagedQueryResult;

import java.time.ZonedDateTime;

@JsonDeserialize(as = DeliveryAddedMessage.class)//important to override annotation in Message class
public class DeliveryAddedMessage extends GenericMessageImpl<Order> {
    public static final String MESSAGE_TYPE = "DeliveryAdded";
    public static final MessageDerivatHint<DeliveryAddedMessage> MESSAGE_HINT =
            MessageDerivatHint.ofSingleMessageType(MESSAGE_TYPE,
                    new TypeReference<PagedQueryResult<DeliveryAddedMessage>>() {
                    },
                    new TypeReference<DeliveryAddedMessage>() {
                    }
            );

    private final Delivery delivery;

    @JsonCreator
    private DeliveryAddedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final Delivery delivery) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type);
        this.delivery = delivery;
    }

    public Delivery getDelivery() {
        return delivery;
    }
}
