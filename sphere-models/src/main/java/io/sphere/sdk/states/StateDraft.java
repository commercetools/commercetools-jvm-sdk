package io.sphere.sdk.states;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Template to create a new State.
 *
 * @see StateDraftBuilder
 */
public class StateDraft extends Base {
    private final String key;
    private final StateType type;
    @Nullable
    private final LocalizedStrings name;
    @Nullable
    private final LocalizedStrings description;
    @Nullable
    private final Boolean initial;
    @Nullable
    private final Set<Reference<State>> transitions;

    StateDraft(String key, StateType type, LocalizedStrings name, LocalizedStrings description,
               Boolean initial, Set<Reference<State>> transitions) {
        this.key = key;
        this.type = type;
        this.name = name;
        this.description = description;
        this.initial = initial;
        this.transitions = transitions;
    }

    public static StateDraft of(final String key, final StateType type) {
        return new StateDraft(key, type, null, null, null, null);
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

    public Boolean isInitial() {
        return initial;
    }

    public Set<Reference<State>> getTransitions() {
        return transitions;
    }

    public StateDraft withName(final LocalizedStrings name) {
        return StateDraftBuilder.of(this).name(name).build();
    }

    public StateDraft withDescription(final LocalizedStrings description) {
        return StateDraftBuilder.of(this).description(description).build();
    }

    public StateDraft withTransitions(@Nullable final Set<Reference<State>> transitions) {
        return StateDraftBuilder.of(this).transitions(transitions).build();
    }

    public StateDraft withInitial(final boolean initial) {
        return StateDraftBuilder.of(this).initial(initial).build();
    }

}
