package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.states.State;

import java.time.ZonedDateTime;

/**
 * Change the state of a Line Item according to allowed transitions
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#transitionLineItemState()}
 */
public final class TransitionLineItemState extends TransitionLineItemLikeState {

    private final String lineItemId;

    private TransitionLineItemState(final String lineItemId, final Long quantity, final Referenceable<State> fromState, final Referenceable<State> toState,
                                    final ZonedDateTime actualTransitionDate) {
        super("transitionLineItemState", quantity, actualTransitionDate, toState.toReference(), fromState.toReference());
        this.lineItemId = lineItemId;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public static TransitionLineItemState of(final String lineItemId, final long quantity,
                                             final Referenceable<State> fromState, final Referenceable<State> toState,
                                             final ZonedDateTime actualTransitionDate) {
        return new TransitionLineItemState(lineItemId, quantity, fromState, toState, actualTransitionDate);
    }

    public static UpdateAction<Order> of(final LineItem lineItem, final long quantity,
                                         final Referenceable<State> fromState, final Referenceable<State> toState,
                                         final ZonedDateTime actualTransitionDate) {
        return of(lineItem.getId(), quantity, fromState, toState, actualTransitionDate);
    }

    public static UpdateAction<Order> of(final LineItem lineItem, final long quantity,
                                         final Referenceable<State> fromState, final Referenceable<State> toState) {
        return of(lineItem.getId(), quantity, fromState, toState, null);
    }
}

