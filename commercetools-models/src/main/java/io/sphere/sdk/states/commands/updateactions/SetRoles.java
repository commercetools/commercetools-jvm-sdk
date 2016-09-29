package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateRole;

import java.util.Set;

/**
 * Sets the roles of a state.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandIntegrationTest#setRoles()}
 */
public final class SetRoles extends UpdateActionImpl<State> {
    private final Set<StateRole> roles;

    private SetRoles(final Set<StateRole> roles) {
        super("setRoles");
        this.roles = roles;
    }

    public Set<StateRole> getRoles() {
        return roles;
    }

    public static SetRoles of(final Set<StateRole> roles) {
        return new SetRoles(roles);
    }
}
