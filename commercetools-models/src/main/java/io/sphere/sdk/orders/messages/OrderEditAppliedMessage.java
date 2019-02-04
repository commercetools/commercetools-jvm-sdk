package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.OrderEditApplied;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderEditAppliedMessage.class)//important to override annotation in Message class
public final class OrderEditAppliedMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "OrderEditApplied";
    public static final MessageDerivateHint<OrderEditAppliedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderEditAppliedMessage.class, Order.referenceTypeId());

    private final Reference<OrderEdit> edit;

    private final OrderEditApplied result;

    @JsonCreator
    private OrderEditAppliedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Reference<OrderEdit> edit, final OrderEditApplied result) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, Order.class);
        this.edit = edit;
        this.result = result;
    }

    public Reference<OrderEdit> getEdit() {
        return edit;
    }

    public OrderEditApplied getResult() {
        return result;
    }
}
