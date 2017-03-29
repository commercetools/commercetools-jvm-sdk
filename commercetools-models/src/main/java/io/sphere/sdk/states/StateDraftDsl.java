package io.sphere.sdk.states;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class StateDraftDsl extends StateDraftDslBase<StateDraftDsl> {

    StateDraftDsl(@Nullable final LocalizedString description, @Nullable @JsonProperty("initial") final Boolean initial, final String key, @Nullable final LocalizedString name, @Nullable final Set<StateRole> roles, @Nullable final Set<Reference<State>> transitions, final StateType type) {
        super(description, initial, key, name, roles, transitions, type);
    }

    public StateDraftDsl withRoles(final StateRole role) {
        return withRoles(Collections.singleton(role));
    }

    public StateDraftDsl withInitial(final boolean initial) {
        return newBuilder().initial(initial).build();
    }

}
