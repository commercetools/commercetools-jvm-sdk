package io.sphere.sdk.states;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Template to create a new State.
 *
 * @see StateDraftBuilder
 */
@JsonDeserialize(as = StateDraftDsl.class)
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"key", "type"}),
additionalBuilderClassContents = "    public StateDraftBuilder roles(final StateRole role) {\n" +
        "        return roles(Collections.singleton(role));\n" +
        "    }",
additionalDslClassContents = "    public StateDraftDsl withRoles(final StateRole role) {\n" +
        "        return withRoles(Collections.singleton(role));\n" +
        "    }")
public interface StateDraft extends WithKey {
    String getKey();

    StateType getType();

    @Nullable
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    @JsonProperty("initial")
    Boolean isInitial();

    @Nullable
    Set<Reference<State>> getTransitions();

    @Nullable
    Set<StateRole> getRoles();

    static StateDraftDsl of(final String key, final StateType type) {
        return StateDraftDsl.of(key, type);
    }
}
