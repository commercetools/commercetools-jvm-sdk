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

/**
 * This message is created when an order is imported.
 *
 * {@include.example io.sphere.sdk.orders.messages.OrderMessagesIntegrationTest#orderImportedMessage()}
 *
 */
@JsonDeserialize(as = OrderImportedMessage.class)//important to override annotation in Message class
public final class OrderImportedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "OrderImported";
    public static final MessageDerivateHint<OrderImportedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderImportedMessage.class, Order.referenceTypeId());

    private final Order order;

    @JsonCreator
    private OrderImportedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Order order) {
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
