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
 <li>Fetch a state by key with {@link io.sphere.sdk.states.queries.StateByKeyFetch}.</li>
 <li>Query a state with {@link io.sphere.sdk.states.queries.StateQuery}.</li>
 <li>Delete a channel with {@link io.sphere.sdk.states.commands.StateDeleteCommand}.</li>
 </ul>
 */
@JsonDeserialize(as = StateImpl.class)
public interface State extends DefaultModel<State> {

    public String getKey();

    public StateType getType();

    public Optional<LocalizedStrings> getName();

    public Optional<LocalizedStrings> getDescription();

    public boolean isInitial();

    public boolean isBuiltIn();

    public Optional<Set<Reference<State>>> getTransitions();

    public default Reference<State> toReference() {
        return Reference.of(typeId(), getId());
    }

    public static String typeId(){
        return "state";
    }

    public static TypeReference<State> typeReference(){
        return new TypeReference<State>() {
            @Override
            public String toString() {
                return "TypeReference<State>";
            }
        };
    }

}
