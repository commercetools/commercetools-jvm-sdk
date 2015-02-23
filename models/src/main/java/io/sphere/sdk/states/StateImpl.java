package io.sphere.sdk.states;


import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

class StateImpl  extends DefaultModelImpl<State> implements State {
    private final String key;
    private final StateType type;
    private final Optional<LocalizedStrings> name;
    private final Optional<LocalizedStrings> description;
    private final boolean initial;
    private final boolean builtIn;
    private final Optional<Set<Reference<State>>> transitions;

    @JsonCreator
    public StateImpl(String id, long version, Instant createdAt, Instant lastModifiedAt, String key, StateType type,
                     Optional<LocalizedStrings> name, Optional<LocalizedStrings> description, boolean initial,
                     boolean builtIn, Optional<Set<Reference<State>>> transitions) {
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

    public Optional<LocalizedStrings> getName() {
        return name;
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }

    public boolean isInitial() {
        return initial;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public Optional<Set<Reference<State>>> getTransitions() {
        return transitions;
    }
}
