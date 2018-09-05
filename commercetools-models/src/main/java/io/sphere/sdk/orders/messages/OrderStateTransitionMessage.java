package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.states.State;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.products.commands.updateactions.TransitionState} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#transitionState()}
 *
 * @see io.sphere.sdk.orders.Order
 * @see Order#getState()
 * @see io.sphere.sdk.orders.commands.updateactions.TransitionState
 */
@JsonDeserialize(as = OrderStateTransitionMessage.class)//important to override annotation in Message class
public final class OrderStateTransitionMessage extends GenericMessageImpl<Order> {
    public static final String MESSAGE_TYPE = "OrderStateTransition";
    public static final MessageDerivateHint<OrderStateTransitionMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderStateTransitionMessage.class, Order.referenceTypeId());


    private final Reference<State> state;

    @JsonCreator
    private OrderStateTransitionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Reference<State> state) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.state = state;
    }

    public Reference<State> getState() {
        return state;
    }
}
