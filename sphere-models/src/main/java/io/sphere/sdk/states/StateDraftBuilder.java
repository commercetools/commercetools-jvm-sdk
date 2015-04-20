package io.sphere.sdk.states;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

public class StateDraftBuilder extends Base implements Builder<StateDraft> {
    private final String key;
    private final StateType type;
    private Optional<LocalizedStrings> name;
    private Optional<LocalizedStrings> description;
    private Optional<Boolean> initial;
    private Optional<Set<Reference<State>>> transitions;

    public StateDraftBuilder(final String key, final StateType type) {
        this.key = key;
        this.type = type;
    }

    public static StateDraftBuilder of(final String key, final StateType type) {
        return new StateDraftBuilder(key, type);
    }

    public static StateDraftBuilder of(final StateDraft template) {
        return new StateDraftBuilder(template.getKey(), template.getType())
                .name(template.getName())
                .description(template.getDescription())
                .transitions(template.getTransitions())
                .initial(template.isInitial());
    }

    public StateDraftBuilder name(final Optional<LocalizedStrings> name) {
        this.name = name;
        return this;
    }

    public StateDraftBuilder name(final LocalizedStrings name) {
        return name(Optional.of(name));
    }

    public StateDraftBuilder description(final Optional<LocalizedStrings> description) {
        this.description = description;
        return this;
    }

    public StateDraftBuilder description(final LocalizedStrings description) {
        return description(Optional.of(description));
    }

    public StateDraftBuilder initial(final Optional<Boolean> initial) {
        this.initial = initial;
        return this;
    }

    public StateDraftBuilder initial(final boolean initial) {
        return initial(Optional.of(initial));
    }

    public StateDraftBuilder transitions(final Optional<Set<Reference<State>>> transitions) {
        this.transitions = transitions;
        return this;
    }

    public StateDraftBuilder transitions(final Set<Reference<State>> transitions) {
        return transitions(Optional.of(transitions));
    }

    @Override
    public StateDraft build() {
        return new StateDraft(key, type, name, description, initial, transitions);
    }

}
