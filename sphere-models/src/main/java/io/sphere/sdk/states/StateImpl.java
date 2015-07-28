package io.sphere.sdk.states;


import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Set;

final class StateImpl extends DefaultModelImpl<State> implements State {
    private final String key;
    private final StateType type;
    @Nullable
    private final LocalizedStrings name;
    @Nullable
    private final LocalizedStrings description;
    private final boolean initial;
    private final boolean builtIn;
    @Nullable
    private final Set<Reference<State>> transitions;

    @JsonCreator
    public StateImpl(String id, Long version, ZonedDateTime createdAt, ZonedDateTime lastModifiedAt, String key, StateType type,
                     LocalizedStrings name, LocalizedStrings description, boolean initial,
                     boolean builtIn, Set<Reference<State>> transitions) {
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

    @Nullable
    public LocalizedStrings getName() {
        return name;
    }

    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }

    public boolean isInitial() {
        return initial;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    @Nullable
    public Set<Reference<State>> getTransitions() {
        return transitions;
    }
}
