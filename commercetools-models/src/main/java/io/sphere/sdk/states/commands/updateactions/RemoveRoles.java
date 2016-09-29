package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateRole;

import java.util.Set;

/**
 * Removes a role from a state.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandIntegrationTest#removeRoles()}
 */
public final class RemoveRoles extends UpdateActionImpl<State> {
    private final Set<StateRole> roles;

    private RemoveRoles(final Set<StateRole> roles) {
        super("removeRoles");
        this.roles = roles;
    }

    public Set<StateRole> getRoles() {
        return roles;
    }

    public static RemoveRoles of(final Set<StateRole> roles) {
        return new RemoveRoles(roles);
    }
}
