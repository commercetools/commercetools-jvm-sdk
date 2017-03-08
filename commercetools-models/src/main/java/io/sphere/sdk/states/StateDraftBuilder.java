package io.sphere.sdk.states;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public final class StateDraftBuilder extends StateDraftBuilderBase<StateDraftBuilder> {

    StateDraftBuilder(@Nullable final LocalizedString description, @Nullable final Boolean initial, final String key, @Nullable final LocalizedString name, @Nullable final Set<StateRole> roles, @Nullable final Set<Reference<State>> transitions, final StateType type) {
        super(description, initial, key, name, roles, transitions, type);
    }

    public static StateDraftBuilder of(final String key, final StateType type) {
        return new StateDraftBuilder(null, null, key, null, null, null, type);
    }

    public static StateDraftBuilder of(final StateDraft template) {
        return new StateDraftBuilder(template.getDescription(), template.isInitial(), template.getKey(), template.getName(), template.getRoles(), template.getTransitions(), template.getType());
    }

    public StateDraftBuilder roles(final StateRole role) {
        return roles(Collections.singleton(role));
    }
}
