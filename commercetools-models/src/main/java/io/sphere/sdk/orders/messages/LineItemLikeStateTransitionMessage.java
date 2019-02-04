package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.states.State;

import java.time.ZonedDateTime;

abstract class LineItemLikeStateTransitionMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    protected final ZonedDateTime transitionDate;
    protected final Long quantity;
    protected final Reference<State> fromState;
    protected final Reference<State> toState;

    public LineItemLikeStateTransitionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Class<Order> clazz, final ZonedDateTime transitionDate, final Long quantity, final Reference<State> fromState, final Reference<State> toState) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.transitionDate = transitionDate;
        this.quantity = quantity;
        this.fromState = fromState;
        this.toState = toState;
    }

    public ZonedDateTime getTransitionDate() {
        return transitionDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Reference<State> getFromState() {
        return fromState;
    }

    public Reference<State> getToState() {
        return toState;
    }
}
