package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
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
@JsonDeserialize(as = LineItemStateTransitionMessage.class)//important to override annotation in Message class
public final class LineItemStateTransitionMessage extends LineItemLikeStateTransitionMessage implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "LineItemStateTransition";
    public static final MessageDerivateHint<LineItemStateTransitionMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, LineItemStateTransitionMessage.class, Order.referenceTypeId());

    private final String lineItemId;

    @JsonCreator
    private LineItemStateTransitionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Class<Order> clazz, final ZonedDateTime transitionDate, final Long quantity, final Reference<State> fromState, final Reference<State> toState, final String lineItemId) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, clazz, transitionDate, quantity, fromState, toState);
        this.lineItemId = lineItemId;
    }

    public String getLineItemId() {
        return lineItemId;
    }

}
