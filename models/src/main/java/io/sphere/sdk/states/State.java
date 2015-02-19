package io.sphere.sdk.states;

import io.sphere.sdk.channels.ChannelImpl;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Optional;
import java.util.Set;

@JsonDeserialize(as = StateImpl.class)
public interface State extends DefaultModel<State> {

    public String getKey();

    public StateType getType();

    public LocalizedStrings getName();

    public LocalizedStrings getDescription();

    public boolean isInitial();

    public boolean isBuiltIn();

    public Optional<Set<State>> getTransitions();

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
