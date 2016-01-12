package io.sphere.sdk.states;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Set;

public final class StateDraftBuilder extends Base implements Builder<StateDraft> {
    private final String key;
    private StateType type;
    @Nullable
    private LocalizedString name;
    @Nullable
    private LocalizedString description;
    @Nullable
    private Boolean initial;
    @Nullable
    private Set<Reference<State>> transitions;
    @Nullable
    private Set<StateRole> roles;

    private StateDraftBuilder(final String key, final StateType type) {
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

    public StateDraftBuilder name(@Nullable final LocalizedString name) {
        this.name = name;
        return this;
    }

    public StateDraftBuilder description(@Nullable final LocalizedString description) {
        this.description = description;
        return this;
    }

    public StateDraftBuilder initial(@Nullable final Boolean initial) {
        this.initial = initial;
        return this;
    }

    public StateDraftBuilder transitions(@Nullable final Set<Reference<State>> transitions) {
        this.transitions = transitions;
        return this;
    }

    public StateDraftBuilder type(final StateType type) {
        this.type = type;
        return this;
    }

    @Override
    public StateDraft build() {
        return new StateDraft(key, type, name, description, initial, transitions, roles);
    }

    public StateDraftBuilder roles(final Set<StateRole> roles) {
        this.roles = roles;
        return this;
    }
}
