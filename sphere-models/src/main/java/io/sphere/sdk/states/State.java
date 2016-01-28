package io.sphere.sdk.states;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Set;

/** A State represents a state of a particular resource (defines a finite state machine). States can be combined together
    by defining transitions between each state, thus allowing to create work-flows.
    Each project has by default an initial LineItemState (inherited also by custom Line Items)

 @see io.sphere.sdk.states.commands.StateCreateCommand
 @see io.sphere.sdk.states.commands.StateUpdateCommand
 @see io.sphere.sdk.states.commands.StateDeleteCommand
 @see io.sphere.sdk.states.queries.StateQuery
 @see io.sphere.sdk.states.queries.StateByIdGet
 @see ItemState#getState()
 @see io.sphere.sdk.orders.Order#getState()
 @see io.sphere.sdk.payments.PaymentStatus#getState()
 @see io.sphere.sdk.products.Product#getState()
 @see io.sphere.sdk.reviews.Review#getState()
 */
@JsonDeserialize(as = StateImpl.class)
public interface State extends Resource<State> {

    String getKey();

    StateType getType();

    @Nullable
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    Boolean isInitial();

    Boolean isBuiltIn();

    @Nullable
    Set<Reference<State>> getTransitions();

    default Reference<State> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static String referenceTypeId() {
        return "state";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
        return "state";
    }

    static TypeReference<State> typeReference(){
        return new TypeReference<State>() {
            @Override
            public String toString() {
                return "TypeReference<State>";
            }
        };
    }

    static Reference<State> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
