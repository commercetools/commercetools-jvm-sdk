package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.commands.UpdateActionImpl;

import java.util.Set;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#removeRoles()}
 */
public class RemoveRoles extends UpdateActionImpl<Channel> {
    private final Set<ChannelRole> roles;

    private RemoveRoles(final Set<ChannelRole> roles) {
        super("removeRoles");
        this.roles = roles;
    }

    public Set<ChannelRole> getRoles() {
        return roles;
    }

    public static RemoveRoles of(final Set<ChannelRole> roles) {
        return new RemoveRoles(roles);
    }
}
