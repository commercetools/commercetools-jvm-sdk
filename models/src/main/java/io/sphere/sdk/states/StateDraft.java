package io.sphere.sdk.states;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

/**
 * Template to create a new State.
 *
 * @see StateDraftBuilder
 */
public class StateDraft extends Base {

    private final String key;
    private final StateType type;
    private final Optional<LocalizedStrings> name;
    private final Optional<LocalizedStrings> description;
    private final boolean initial;
    private final boolean builtIn;
    private final Optional<Set<Reference<State>>> transitions;


    StateDraft(String key, StateType type, Optional<LocalizedStrings> name, Optional<LocalizedStrings> description, boolean initial, boolean builtIn,
                     Optional<Set<Reference<State>>> transitions) {
        this.key = key;
        this.type = type;
        this.name = name;
        this.description = description;
        this.initial = initial;
        this.builtIn = builtIn;
        this.transitions = transitions;
    }

    public static StateDraft of(final String key, final StateType type, final boolean initial, final boolean builtIn) {
        return new StateDraft(key, type, Optional.empty(), Optional.empty(), initial, builtIn, Optional.empty());
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

    public StateDraft withName(final LocalizedStrings name) {
        return StateDraftBuilder.of(this).name(name).build();
    }

    public StateDraft withDescription(final LocalizedStrings description) {
        return StateDraftBuilder.of(this).description(description).build();
    }

    public StateDraft withTransitions(final Set<Reference<State>> transitions) {
        return StateDraftBuilder.of(this).transitions(transitions).build();
    }

}
