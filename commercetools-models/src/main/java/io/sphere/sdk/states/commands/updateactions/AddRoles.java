package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateRole;

import java.util.Set;

/**
 *  Adds roles to a state.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.states.commands.StateUpdateCommandIntegrationTest#addRoles()}
 *
 *  @see State#getRoles()
 */
public final class AddRoles extends UpdateActionImpl<State> {
    private final Set<StateRole> roles;

    private AddRoles(final Set<StateRole> roles) {
        super("addRoles");
        this.roles = roles;
    }

    public Set<StateRole> getRoles() {
        return roles;
    }

    public static AddRoles of(final Set<StateRole> roles) {
        return new AddRoles(roles);
    }
}
