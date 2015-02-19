package io.sphere.sdk.states;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

class StateImpl  extends DefaultModelImpl<State> implements State {

    private final String key;
    private final StateType type;
    private final LocalizedStrings name;
    private final LocalizedStrings description;
    private final boolean initial;
    private final boolean builtIn;
    private final Optional<Set<State>> transitions;

    @JsonCreator
    public StateImpl(String id, long version, Instant createdAt, Instant lastModifiedAt, String key, StateType type,
                     LocalizedStrings name, LocalizedStrings description, boolean initial, boolean builtIn,
                     Optional<Set<State>> transitions) {
        super(id, version, createdAt, lastModifiedAt);
        this.key = key;
        this.type = type;
        this.name = name;
        this.description = description;
        this.initial = initial;
        this.builtIn = builtIn;
        this.transitions = transitions;
    }

    public String getKey() {
        return key;
    }

    public StateType getType() {
        return type;
    }

    public LocalizedStrings getName() {
        return name;
    }

    public LocalizedStrings getDescription() {
        return description;
    }

    public boolean isInitial() {
        return initial;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public Optional<Set<State>> getTransitions() {
        return transitions;
    }
}
