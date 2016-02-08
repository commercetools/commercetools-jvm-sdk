package io.sphere.sdk.states;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Template to create a new State.
 *
 * @see StateDraftBuilder
 */
@JsonDeserialize(as = StateDraftDsl.class)
public interface StateDraft {
    String getKey();

    StateType getType();

    @Nullable
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    Boolean isInitial();

    @Nullable
    Set<Reference<State>> getTransitions();

    @Nullable
    Set<StateRole> getRoles();

    static StateDraftDsl of(final String key, final StateType type) {
        return new StateDraftDsl(key, type, null, null, null, null, null);
    }
}
