package io.sphere.sdk.states;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
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
public interface State extends DefaultModel<State> {

    String getKey();

    StateType getType();

    Optional<LocalizedStrings> getName();

    Optional<LocalizedStrings> getDescription();

    boolean isInitial();

    boolean isBuiltIn();

    Optional<Set<Reference<State>>> getTransitions();

    default Reference<State> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

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
