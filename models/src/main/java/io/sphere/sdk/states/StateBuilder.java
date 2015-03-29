package io.sphere.sdk.states;

import io.sphere.sdk.models.DefaultModelFluentBuilder;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

/**
 * Creates a state for unit tests.
 *
 */
public class StateBuilder extends DefaultModelFluentBuilder<StateBuilder, State> {
    private String key;
    private StateType type;
    private Optional<LocalizedStrings> name;
    private Optional<LocalizedStrings> description;
    private boolean initial;
    private boolean builtIn;
    private Optional<Set<Reference<State>>> transitions;

    public StateBuilder(final String id, final String key, final StateType type, final boolean initial, final boolean builtIn) {
        this.id = id;
        this.key = key;
        this.type = type;
        this.initial = initial;
        this.builtIn = builtIn;
    }

    public static StateBuilder of(final String id, final String key, final StateType type, final boolean initial, final boolean builtIn) {
        return new StateBuilder(id, key, type, initial, builtIn);
    }

    public static StateBuilder of(final State state) {
        return new StateBuilder(state.getId(), state.getKey(), state.getType(), state.isInitial(), state.isBuiltIn())
                .name(state.getName())
                .description(state.getDescription())
                .transitions(state.getTransitions());
    }

    public StateBuilder key(final String value) {
        this.key = value;
        return this;
    }

    public StateBuilder type(final StateType value) {
        this.type = value;
        return this;
    }

    public StateBuilder name(final Optional<LocalizedStrings> value) {
        this.name = value;
        return this;
    }

    public StateBuilder name(final LocalizedStrings value) {
        this.name = Optional.ofNullable(value);
        return this;
    }

    public StateBuilder description(final Optional<LocalizedStrings> value) {
        this.description = value;
        return this;
    }

    public StateBuilder description(final LocalizedStrings value) {
        this.description = Optional.ofNullable(value);
        return this;
    }

    public StateBuilder initial(final boolean value) {
        this.initial = value;
        return this;
    }

    public StateBuilder builtIn(final boolean value) {
        this.builtIn = value;
        return this;
    }

    public StateBuilder transitions(final Optional<Set<Reference<State>>> value) {
        this.transitions = value;
        return this;
    }

    public StateBuilder transitions(final Set<Reference<State>> value) {
        this.transitions = Optional.ofNullable(value);
        return this;
    }

    @Override
    public State build() {
        return new StateImpl(id, version, createdAt, lastModifiedAt, key, type, name, description, initial, builtIn, transitions);
    }

    @Override
    protected StateBuilder getThis() {
        return this;
    }
}
