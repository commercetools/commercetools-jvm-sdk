package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.commands.UpdateAction;

import java.util.Set;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#removeRoles()}
 */
public class RemoveRoles extends UpdateAction<Channel> {
    private final Set<ChannelRoles> roles;

    private RemoveRoles(final Set<ChannelRoles> roles) {
        super("removeRoles");
        this.roles = roles;
    }

    public Set<ChannelRoles> getRoles() {
        return roles;
    }

    public static RemoveRoles of(final Set<ChannelRoles> roles) {
        return new RemoveRoles(roles);
    }
}
