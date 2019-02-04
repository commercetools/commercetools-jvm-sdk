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
 * This message is the result of the {@link io.sphere.sdk.orders.commands.updateactions.TransitionCustomLineItemState} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#transitionCustomLineItemState()}
 *
 */
@JsonDeserialize(as = CustomLineItemStateTransitionMessage.class)//important to override annotation in Message class
public final class CustomLineItemStateTransitionMessage extends LineItemLikeStateTransitionMessage implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "CustomLineItemStateTransition";
    public static final MessageDerivateHint<CustomLineItemStateTransitionMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CustomLineItemStateTransitionMessage.class, Order.referenceTypeId());

    private final String customLineItemId;

    @JsonCreator
    private CustomLineItemStateTransitionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Class<Order> clazz, final ZonedDateTime transitionDate, final Long quantity, final Reference<State> fromState, final Reference<State> toState, final String customLineItemId) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, clazz, transitionDate, quantity, fromState, toState);
        this.customLineItemId = customLineItemId;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

}
