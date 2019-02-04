package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderCreatedMessage.class)//important to override annotation in Message class
public final class OrderCreatedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "OrderCreated";
    public static final MessageDerivateHint<OrderCreatedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderCreatedMessage.class, Order.referenceTypeId());

    private final Order order;

    @JsonCreator
    private OrderCreatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Order order) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.order = order;
    }

    /**
     * Gets the order object at creation time. This output can differ from an expanded {@link Message#getResource()}.
     *
     * @return the order at creation time
     */
    public Order getOrder() {
        return order;
    }
}
