package io.sphere.sdk.states;


import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Set;

final class StateImpl extends ResourceImpl<State> implements State {
    private final String key;
    private final StateType type;
    @Nullable
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    private final Boolean initial;
    private final Boolean builtIn;
    @Nullable
    private final Set<Reference<State>> transitions;

    @JsonCreator
    public StateImpl(String id, Long version, ZonedDateTime createdAt, ZonedDateTime lastModifiedAt, String key, StateType type,
                     @Nullable LocalizedString name, @Nullable LocalizedString description, Boolean initial,
                     Boolean builtIn, @Nullable Set<Reference<State>> transitions) {
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
    public LocalizedString getName() {
        return name;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    public Boolean isInitial() {
        return initial;
    }

    public Boolean isBuiltIn() {
        return builtIn;
    }

    @Nullable
    public Set<Reference<State>> getTransitions() {
        return transitions;
    }
}
