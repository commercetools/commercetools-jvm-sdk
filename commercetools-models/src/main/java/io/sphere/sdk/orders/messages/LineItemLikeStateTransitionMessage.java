package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.states.State;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.orders.commands.updateactions.TransitionLineItemState} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#transitionLineItemState()}
 *
 */
@JsonDeserialize(as = LineItemLikeStateTransitionMessage.class)//important to override annotation in Message class
public final class LineItemLikeStateTransitionMessage extends LineItemLikeStateTransition {

    public static final String MESSAGE_TYPE = "LineItemStateTransition";
    public static final MessageDerivateHint<LineItemLikeStateTransitionMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, LineItemLikeStateTransitionMessage.class, Order.referenceTypeId());

    private final String lineItemId;

    @JsonCreator
    public LineItemLikeStateTransitionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final Class<Order> clazz, final ZonedDateTime transitionDate, final Long quantity, final Reference<State> fromState, final Reference<State> toState, final String lineItemId) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, clazz, transitionDate, quantity, fromState, toState);
        this.lineItemId = lineItemId;
    }

    public String getLineItemId() {
        return lineItemId;
    }

}
