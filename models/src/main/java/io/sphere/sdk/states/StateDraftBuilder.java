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
    private final boolean initial;
    private final boolean builtIn;
    private Optional<Set<Reference<State>>> transitions;

    public StateDraftBuilder(final String key, final StateType type, final boolean initial, final boolean builtIn) {
        this.key = key;
        this.type = type;
        this.initial = initial;
        this.builtIn = builtIn;
    }

    public static StateDraftBuilder of(final String key, final StateType type, final boolean initial, final boolean builtIn) {
        return new StateDraftBuilder(key, type, initial, builtIn);
    }

    public static StateDraftBuilder of(final StateDraft template) {
        return new StateDraftBuilder(template.getKey(), template.getType(), template.isInitial(), template.isBuiltIn())
                .name(template.getName())
                .description(template.getDescription())
                .transitions(template.getTransitions());
    }

    public StateDraftBuilder name(final Optional<LocalizedStrings> name) {
        this.name = name;
        return this;
    }

    public StateDraftBuilder name(final LocalizedStrings name) {
        return name(Optional.ofNullable(name));
    }

    public StateDraftBuilder description(final Optional<LocalizedStrings> description) {
        this.description = description;
        return this;
    }

    public StateDraftBuilder description(final LocalizedStrings description) {
        return description(Optional.ofNullable(description));
    }

    public StateDraftBuilder transitions(final Optional<Set<Reference<State>>> transitions) {
        this.transitions = transitions;
        return this;
    }

    public StateDraftBuilder transitions(final Set<Reference<State>> transitions) {
        return transitions(Optional.ofNullable(transitions));
    }

    @Override
    public StateDraft build() {
        return new StateDraft(key, type, name, description, initial, builtIn, transitions);
    }

}
