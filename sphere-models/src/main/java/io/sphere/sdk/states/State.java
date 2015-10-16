package io.sphere.sdk.states;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Set;

/** A State represents a state of a particular entity (defines a finite state machine). States can be combined together
    by defining transitions between each state, thus allowing to create work-flows.
    Each project has by default an initial LineItemState (inherited also by custom Line Items)

 <p id=operations>Operations:</p>
 <ul>
 <li>Create a state with {@link io.sphere.sdk.states.commands.StateCreateCommand}.</li>
 <li>Query a state with {@link io.sphere.sdk.states.queries.StateQuery}.</li>
 <li>Delete a channel with {@link io.sphere.sdk.states.commands.StateDeleteCommand}.</li>
 </ul>
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

    static String referenceTypeId(){
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

}
