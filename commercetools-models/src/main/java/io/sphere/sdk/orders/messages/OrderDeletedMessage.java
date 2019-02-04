package io.sphere.sdk.orders.messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderDeletedMessage.class)//important to override annotation in Message class
public final class OrderDeletedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "OrderDeleted";
    public static final MessageDerivateHint<OrderDeletedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderDeletedMessage.class, Order.referenceTypeId());


    private final Order order;

    @JsonCreator
    private OrderDeletedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                                final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Order order) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
